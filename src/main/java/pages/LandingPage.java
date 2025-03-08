package pages;

import components.ProductComponent;
import constants.WaitTime;
import exceptions.ProductNotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LandingPage extends BasePage {

	// Core elements
	@FindBy(xpath = "//select[@class='product_sort_container']")
	private WebElement sortDropdown;

	@FindBy(xpath = "//div[@class='inventory_item']")
	private List<WebElement> productContainers;

	// Product components
	private List<ProductComponent> products;

	public enum SortOption {
		PRICE_LOW_HIGH("lohi"), PRICE_HIGH_LOW("hilo"), A_TO_Z("az"), Z_TO_A("za");

		private final String value;

		SortOption(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public LandingPage(WebDriver driver) {
		super(driver);
		initializeProductComponents();
	}

	private void initializeProductComponents() {
		products = productContainers.stream().map(container -> new ProductComponent(driver, container))
				.collect(Collectors.toList());
	}

	// Core Page Actions
	public LandingPage selectSortOption(SortOption option) {

		executeWithLogging(sortDropdown, "Select sort option", () -> {
			new Select(sortDropdown).selectByValue(option.getValue());
			waitForElementStale(productContainers.get(0));
			refreshProductList();
		});

		// Verify sorting is actually applied
		if (option == SortOption.PRICE_LOW_HIGH || option == SortOption.PRICE_HIGH_LOW) {
			if (!verifyProductSortingByPrice(option)) {
				throw new AssertionError("Product sorting failed for: " + option);
			}
		} else if (option == SortOption.A_TO_Z || option == SortOption.Z_TO_A) {
			if (!verifyProductSortingByName(option)) {
				throw new AssertionError("Product name sorting failed for: " + option);
			}
		}

		return this;
	}

	public boolean verifyProductSortingByName(SortOption option) {

		List<String> names = products.stream().map(ProductComponent::getProductName).collect(Collectors.toList());

		List<String> sortedNames = new ArrayList<>(names);
		if (option == SortOption.A_TO_Z) {
			sortedNames.sort(Comparator.naturalOrder());
		} else if (option == SortOption.Z_TO_A) {
			sortedNames.sort(Comparator.reverseOrder());
		} else {
			throw new UnsupportedOperationException("Invalid sort option for names.");
		}

		return names.equals(sortedNames);
	}

	public boolean verifyProductSortingByPrice(SortOption option) {
		// Refresh product list to ensure we're checking the latest elements
		refreshProductList();

		List<Double> prices = products.stream().map(ProductComponent::getProductPrice)
				.map(price -> Double.parseDouble(price.replace("$", ""))).collect(Collectors.toList());

		// Create a sorted version of the prices based on the selected sorting option
		List<Double> sortedPrices = new ArrayList<>(prices);
		sortedPrices.sort(getPriceComparator(option));

		return prices.equals(sortedPrices);
	}

	public LandingPage addItemToCart(int index) {

		validateIndexBoundaries(index);
		products.get(index).addProductToCart();
		verifyCartCountChange(1);
		return this;
	}

	public LandingPage removeItemFromCart(int index) {

		validateIndexBoundaries(index);
		products.get(index).removeProductFromCart();
		verifyCartCountChange(1);
		return this;
	}

	public LandingPage addItemToCart(String name) {

		products.stream()
				.filter(p -> p.getProductName()
				.equalsIgnoreCase(name))
				.findFirst()
				.ifPresent(p -> p.addProductToCart());

		return this;
	}

	public LandingPage removeItemFromCart(String name) {

		products.stream()
				.filter(p -> p.getProductName()
				.equalsIgnoreCase(name))
				.findFirst()
				.ifPresent(p -> p.removeProductFromCart());

		return this;
	}

	public ProductDetailsPage viewProductDetails(int index) {

		validateIndexBoundaries(index);
		clickElement(productContainers.get(index));
		return products.get(index).viewDetails();
	}

	public ProductComponent getProductByIndex(int index) {

		validateIndexBoundaries(index);
		return products.get(index);
	}

	public ProductComponent getProductByName(String name) {

		return products.stream().filter(p -> p.getProductName().equalsIgnoreCase(name)).findFirst()
				.orElseThrow(() -> new ProductNotFoundException(name));
	}

	public ProductComponent getProductByExactPrice(double price) {

		String formattedPrice = String.format("$%.2f", price);
		return products.stream().filter(p -> p.getProductPrice().equals(formattedPrice)).findFirst()
				.orElseThrow(() -> new ProductNotFoundException("Price: " + price));
	}

	private Comparator<Double> getPriceComparator(SortOption option) {

		if (option == SortOption.PRICE_HIGH_LOW) {
			return Comparator.reverseOrder();
		} else if (option == SortOption.PRICE_LOW_HIGH) {
			return Comparator.naturalOrder();
		}
		throw new UnsupportedOperationException("Sorting by name should be handled differently.");
	}

	private void validateIndexBoundaries(int index) {
		if (index < 0 || index >= products.size()) {
			throw new IndexOutOfBoundsException(
					String.format("Index %d out of bounds for product count %d", index, products.size()));
		}
	}

	private void verifyCartCountChange(int expectedChange) {

		int initialCount = cart.getCurrentCartCount();
		boolean cartUpdated = customWait.until(d -> cart.getCurrentCartCount() == initialCount + expectedChange,
				WaitTime.NORMAL);
		if (!cartUpdated) {
			throw new AssertionError("Cart count did not update as expected.");
		}
	}

	private void refreshProductList() {
		products = productContainers.stream().map(container -> new ProductComponent(driver, container))
				.collect(Collectors.toList());
	}

}
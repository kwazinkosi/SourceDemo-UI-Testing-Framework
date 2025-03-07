package constants;

import utils.ConfigReader;

public final class WaitTime {

	public static final int SLOW = Integer.parseInt(ConfigReader.getProperty("slow_wait_time"));
	public static final int NORMAL = Integer.parseInt(ConfigReader.getProperty("normal_wait_time"));
	public static final int FAST = Integer.parseInt(ConfigReader.getProperty("fast_wait_time"));
	public static final int FASTER = Integer.parseInt(ConfigReader.getProperty("faster_wait_time"));

	private WaitTime() {} // Prevent instantiation
}
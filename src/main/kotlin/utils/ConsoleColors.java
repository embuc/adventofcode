package utils;

public class ConsoleColors {
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String MAGENTA = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";
	public static final String RED_BOLD = "\u001B[1;31m"; // RED BOLD
	public static final String YELLOW_BOLD = "\u001B[1;33m"; // YELLOW BOLD
	public static final String GRAY = "\u001B[90m"; // Bright black/Gray
	public static final String GRAY_STANDARD = "\u001B[30m"; // Normal Black/Gray
	public static final String LIGHT_GRAY = "\u001B[37m"; // Light Gray/White

	public static void printRed(String message) {
		System.out.print(RED + message + RESET);
	}

	public static void printGreen(String message) {
		System.out.print(GREEN + message + RESET);
	}

	public static void printYellow(String message) {
		System.out.print(YELLOW + message + RESET);
	}

	public static void printBlue(String message) {
		System.out.print(BLUE + message + RESET);
	}

	public static void printMagenta(String message) {
		System.out.print(MAGENTA + message + RESET);
	}

	public static void printCyan(String message) {
		System.out.print(CYAN + message + RESET);
	}

	public static void printWhite(String message) {
		System.out.print(WHITE + message + RESET);
	}

	public static void printRedBold(String message) {
		System.out.print(RED_BOLD + message + RESET);
	}

	public static void printYellowBold(String message) {
		System.out.print(YELLOW_BOLD + message + RESET);
	}

	public static void printGray(String message) {
		System.out.print(GRAY + message + RESET);
	}

	public static void printDarkGray(String message) {
		System.out.print(GRAY_STANDARD + message + RESET);
	}

	public static void printLightGray(String message) {
		System.out.print(LIGHT_GRAY + message + RESET);
	}

	// Helper methods for println
	public static void printlnRed(String message) {
		System.out.println(RED + message + RESET);
	}

	public static void printlnGreen(String message) {
		System.out.println(GREEN + message + RESET);
	}

	public static void printlnYellow(String message) {
		System.out.println(YELLOW + message + RESET);
	}

	public static void printlnBlue(String message) {
		System.out.println(BLUE + message + RESET);
	}

	public static void printlnMagenta(String message) {
		System.out.println(MAGENTA + message + RESET);
	}

	public static void printlnCyan(String message) {
		System.out.println(CYAN + message + RESET);
	}

	public static void printlnWhite(String message) {
		System.out.println(WHITE + message + RESET);
	}

	public static void printlnRedBold(String message) {
		System.out.println(RED_BOLD + message + RESET);
	}

	public static void printlnYellowBold(String message) {
		System.out.println(YELLOW_BOLD + message + RESET);
	}

	public static void printlnGray(String message) {
		System.out.println(GRAY + message + RESET);
	}

	public static void printlnDarkGray(String message) {
		System.out.println(GRAY_STANDARD + message + RESET);
	}

	public static void printlnLightGray(String message) {
		System.out.println(LIGHT_GRAY + message + RESET);
	}
}

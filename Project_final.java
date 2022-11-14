import java.util.*;

class Connect4 {

    private String player1;
    private String player2;

    private String board[][];
    private boolean isPlayer1;
    private int counter;
    private int columnCounter[];
    private Set<Integer> availableColumns;
    private String colour;

    public void start(){
			System.out.println("  _____ ____  _   _ _   _ ______ _____ _______   _  _      _____          __  __ ______ \n" +
												 " / ____/ __ \\| \\ | | \\ | |  ____/ ____|__   __| | || |    / ____|   /\\   |  \\/  |  ____|\n" +
												 "| |   | |  | |  \\| |  \\| | |__ | |       | |    | || |_  | |  __   /  \\  | \\  / | |__   \n" +
												 "| |   | |  | | . ` | . ` |  __|| |       | |    |__   _| | | |_ | / /\\ \\ | |\\/| |  __|  \n" +
												 "| |___| |__| | |\\  | |\\  | |___| |____   | |       | |   | |__| |/ ____ \\| |  | | |____ \n" +
												 " \\_____\\____/|_| \\_|_| \\_|______\\_____|  |_|       |_|    \\_____/_/    \\_\\_|  |_|______|");
			System.out.println("----------------------------------------------------------------------------------------");
			typewriter("				WELCOME TO THE CONNECT 4 GAME				");
			System.out.println("----------------------------------------------------------------------------------------");
        Scanner sc = new Scanner(System.in);

        System.out.println("Player1 please enter your name: ");
        this.player1 = sc.nextLine().toUpperCase();

        while (player1.length() < 4){
            System.out.println("The name should contain at least 4 characters. Please enter a valid name ");
            this.player1 = sc.nextLine().toUpperCase();
        }

        System.out.println("Player2 please enter your name: ");
        this.player2 = sc.nextLine().toUpperCase();

        while (player2.length() < 4 || this.player1.equals(this.player2)){
            if(this.player1.equals(this.player2)){
                System.out.println("The name of player2 and player1 can't be same. Please enter a different name: ");
            }
            else
                System.out.println("The name should contain at least 4 characters. Please enter a valid name: ");

            player2 = sc.nextLine().toUpperCase();
        }

        loadTheGame();
    }

    private void loadTheGame(){
        typewriter("The game is loading.....");
        this.board = new String[7][6];
        this.counter = 0;
        this.columnCounter = new int[]{7,7,7,7,7,7};
        this.availableColumns = new LinkedHashSet<>(List.of(1,2,3,4,5,6));

        for(String arr[]:this.board){
            Arrays.fill(arr,"⚪️");
        }

        Random rand = new Random();
        int num = rand.nextInt(2);
        if(num == 0){
            this.isPlayer1 = true;
        }
        else
            this.isPlayer1 = false;

        this.colour = "NONE";
        startTheGame();
    }

    private void typewriter(String s){
        for (int i = 0; i <s.length(); i++) {
            System.out.print(s.charAt(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }
    private void startTheGame(){
        if(this.counter == 42){
						printBoard();
            typewriter("The Game is over. ");
						System.out.println("\n" +
                            "  _____   _______   _    _____                         _____    _____              __          __\n" +
                            " |_   _| |__   __| ( )  / ____|           /\\          |  __ \\  |  __ \\      /\\     \\ \\        / /\n" +
                            "   | |      | |    |/  | (___            /  \\         | |  | | | |__) |    /  \\     \\ \\  /\\  / / \n" +
                            "   | |      | |         \\___ \\          / /\\ \\        | |  | | |  _  /    / /\\ \\     \\ \\/  \\/ /  \n" +
                            "  _| |_     | |         ____) |        / ____ \\       | |__| | | | \\ \\   / ____ \\     \\  /\\  /   \n" +
                            " |_____|    |_|        |_____/        /_/    \\_\\      |_____/  |_|  \\_\\ /_/    \\_\\     \\/  \\/    \n" +
                            "                                                                                                 \n" +
                            "                                                                                                 \n");

						playAgain();
            return;
        }

        printBoard();
        int column = getInput() - 1;

        if(isPlayer1){
            this.colour = "🔴";
        }
        else{
            this.colour = "🔵";
        }

        int row = this.columnCounter[column] - 1;
        this.board[row][column] = this.colour;

        this.columnCounter[column] -= 1;
        if(this.columnCounter[column] == 0){
            this.availableColumns.remove(column + 1);
        }

        this.counter++;
        if(this.counter >= 7){
            if(gameCompleted(row, column, this.colour)){
                printBoard();
								if(isPlayer1){
                    typewriter(this.player1 + " Congrats !!");
										System.out.println("\n" +
                            " __     __   ____    _    _       __          __   ____    _   _     _   _  \n" +
                            " \\ \\   / /  / __ \\  | |  | |      \\ \\        / /  / __ \\  | \\ | |   | | | | \n" +
                            "  \\ \\_/ /  | |  | | | |  | |       \\ \\  /\\  / /  | |  | | |  \\| |   | | | | \n" +
                            "   \\   /   | |  | | | |  | |        \\ \\/  \\/ /   | |  | | | . ` |   | | | | \n" +
                            "    | |    | |__| | | |__| |         \\  /\\  /    | |__| | | |\\  |   |_| |_| \n" +
                            "    |_|     \\____/   \\____/           \\/  \\/      \\____/  |_| \\_|   (_) (_) \n" +
                            "                                                                            \n" +
                            "                                                                            \n");

                }
                else{
									typewriter(this.player2 + " Congrats !!");
									System.out.println("\n" +
													" __     __   ____    _    _       __          __   ____    _   _     _   _  \n" +
													" \\ \\   / /  / __ \\  | |  | |      \\ \\        / /  / __ \\  | \\ | |   | | | | \n" +
													"  \\ \\_/ /  | |  | | | |  | |       \\ \\  /\\  / /  | |  | | |  \\| |   | | | | \n" +
													"   \\   /   | |  | | | |  | |        \\ \\/  \\/ /   | |  | | | . ` |   | | | | \n" +
													"    | |    | |__| | | |__| |         \\  /\\  /    | |__| | | |\\  |   |_| |_| \n" +
													"    |_|     \\____/   \\____/           \\/  \\/      \\____/  |_| \\_|   (_) (_) \n" +
													"                                                                            \n" +
													"                                                                            \n");
								}
								playAgain();
                return;
            }
        }

        this.isPlayer1 = !this.isPlayer1;
        startTheGame();
    }

    private int getInput(){
        StringBuilder availableDigits = new StringBuilder();

        for(Integer num: this.availableColumns){
            availableDigits.append(num);
            availableDigits.append(" ");
        }

        System.out.println("The available Columns are: " + availableDigits);

        if(this.isPlayer1){
            System.out.println("\""+player1+"\"" + " please choose a column no. ");
        }
        else{
            System.out.println("\""+player2+"\"" + " please choose a column no. ");
        }

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (input.length() == 0 || !Character.isDigit(input.charAt(input.length()-1)) || !this.availableColumns.contains(input.charAt(input.length()-1) - '0')){
            if (input.length() == 0){
                System.out.println("The input can't be empty and it has to be a digit from the following. "+ availableDigits+" ");
            }
            else {
                System.out.println("The input has to be a digit from the following. "+ availableDigits+" ");
            }
            input = sc.nextLine();
        }

        int column = input.charAt(input.length()-1) - '0';

        return column;
    }

    private boolean gameCompleted(int row, int column , String colour){
        return isFilledColumn(row, column, colour) || isFilledRow(row, column, colour) || isFilledDiagonal(row, column, colour);
    }
    
    
    private void printBoard(){
        StringBuilder currentBoard = new StringBuilder();
        int count = 0;
	currentBoard.append("1  2  3  4  5  6");
	currentBoard.append("\n");
	currentBoard.append("\n");
        for(String arr[]: this.board){
            for(String word: arr){
                currentBoard.append(word);
                currentBoard.append("  ");
            }
            count++;
            if(count != 7){
                currentBoard.append("\n");
                currentBoard.append("\n");
            }
        }

        System.out.println(currentBoard);
    }

    private boolean isFilledDiagonal(int row, int column, String color){
        int count = 1;
        int rowTemp = row-1;
        int colTemp = column-1;

        while(rowTemp >= 0 && colTemp >= 0) {
            if (this.board[rowTemp][colTemp].equals(color)) {
                count++;
                if (count == 4) {
                    return true;
                }
                rowTemp--;
                colTemp--;
            }
            else
                break;
        }

        rowTemp = row + 1;
        colTemp = column + 1;

        while(rowTemp < 7 && colTemp < 6) {
            if (this.board[rowTemp][colTemp].equals(color)) {
                count++;
                if (count == 4) {
                    return true;
                }
                rowTemp++;
                colTemp++;
            }
            else
                break;
        }

        count = 1;
        rowTemp = row - 1;
        colTemp = column + 1;

        while(rowTemp >= 0 && colTemp < 6) {
            if (this.board[rowTemp][colTemp].equals(color)) {
                count++;
                if (count == 4) {
                    return true;
                }
                rowTemp--;
                colTemp++;
            }
            else
                break;
        }

        rowTemp = row + 1;
        colTemp = column - 1;

        while(rowTemp < 7 && colTemp >= 0) {
            if (this.board[rowTemp][colTemp].equals(color)) {
                count++;
                if (count == 4) {
                    return true;
                }
                rowTemp++;
                colTemp--;
            }
            else
                break;
        }

        return false;
    }

    private boolean isFilledRow(int row, int column, String color){
        int count = 1;

        for(int colTemp = column -1; colTemp>=0; colTemp--){
            if(this.board[row][colTemp].equals(color)){
                count++;
                if(count  == 4){
                    return true;
                }
            }
            else
                break;
        }

        for(int colTemp = column+1; colTemp<6; colTemp++){
            if(this.board[row][colTemp].equals(color)){
                count++;
                if(count  == 4){
                    return true;
                }
            }
            else
                break;
        }
        return false;
    }

    private boolean isFilledColumn(int row, int column, String color){
        int count = 1;

        for(int rowTemp = row -1; rowTemp>=0; rowTemp--){
            if(this.board[rowTemp][column].equals(color)){
                count++;
                if(count  == 4){
                    return true;
                }
            }
            else
                break;
        }

        for(int rowTemp = row+1; rowTemp<7; rowTemp++){
            if(this.board[rowTemp][column].equals(color)){
                count++;
                if(count  == 4){
                    return true;
                }
            }
            else
                break;
        }

        return false;
    }

	private void playAgain(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to play again --> Yes -> y No --> enter any other key");
        String playAgain = sc.nextLine().toUpperCase();
        if(playAgain.equals("Y")){
            start();
        }
    }

}

public class Project_final {

	public static void main(String[] args) {
		Connect4 game = new Connect4();
		game.start();

	}

}

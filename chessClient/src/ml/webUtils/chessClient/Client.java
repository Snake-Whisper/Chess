package ml.webUtils.chessClient;

class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Field field = new Field();
	}
	
	static public void sendMove(int [][] coors) {
		System.out.println("Sending Move from ("+coors[0][0]+"|"+coors[0][1]+") to ("+coors[1][0]+"|"+coors[1][1]+")");
	}

}

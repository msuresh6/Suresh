import javax.swing.JFrame;

public class MainGame {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		PlayGame playGame = new PlayGame();
		
		obj.setBounds(10, 10, 600, 600);
		obj.setTitle("Brick Game");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(playGame);
		

	}

}

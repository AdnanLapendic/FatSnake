package ba.bitcamp.week7.day45.snake;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JFrame {

	private MyPanel panel = new MyPanel();

	private int x = 300;
	private int y = 300;
	private int deltaX = 1;
	private int deltaY = 0;
	private int foodX;
	private int foodY;
	private int size = 10;
	private int score = 0;
	private JLabel label = new JLabel();
	private JLabel label2 = new JLabel();
	private int counter = 0;
	Rectangle[] point = new Rectangle[1];
	private JLabel gitHub = new JLabel("GitHub");

	private static final long serialVersionUID = -6912466392784209001L;

	Rectangle s = new Rectangle(x, y, 50, 50);
	Rectangle f = new Rectangle(foodX, foodY, 100, 100);

	Timer time = new Timer(10, new Action());
	Timer foodTime = new Timer(10000, new Action2());
	Timer scoreTime = new Timer(1000, new Action3());

	public Snake() {
		panel.add(label);
		panel.add(label2);
		label.setLocation(290, 50);
		label2.setLocation(350, 50);
		label2.setForeground(Color.BLUE);
		label.setForeground(Color.RED);
		System.out.println(f.intersects(s));
		time.start();
		foodTime.start();
		scoreTime.start();
		add(panel);
		setTitle("Fat Snake");
		addKeyListener(new Key());
		setSize(600, 600);
		panel.setBounds(10, 10, getWidth() - 20, getHeight() - 20);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
		panel.setBackground(Color.GREEN);
		foodX = (int) (Math.random() * 600);
		foodY = (int) (Math.random() * 600);
		label.setText("Score=" + score);
		panel.add(gitHub);
		gitHub.setForeground(Color.BLACK);
		gitHub.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource().equals(gitHub)){
					try {
						Desktop.getDesktop().browse(new URI("https://github.com/ZaidZerdo/W9D3/blob/master/MyList.java"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				super.mouseClicked(e);
			}
		});

		setVisible(true);
	}

	private class MyPanel extends JPanel {

		private static final long serialVersionUID = -8328349862308317225L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(Color.RED);

			for (int i = 0; i < point.length; i++) {
				s = new Rectangle(x, y, size, size);
				g.fillRect((int) s.getX() + deltaX, (int) s.getY() + deltaY,
						size, size);

			}
			repaint();

			f = new Rectangle(foodX, foodY, 10, 10);
			g.fillRect((int) f.getX(), (int) f.getY(), 10, 10);

			Rectangle s2 = new Rectangle(x + 5, y + 5, size, size);

			if (s.intersects(f)) {
				size+=1;//increase size if food collected
				resizeSnake();
				addRectangle(s2);

				repaint();
			}
		}
	}
	
	

	private class Action implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			x += deltaX;
			y += deltaY;

			if (f.intersects(s)) {

				score += 10;
				label.setText("Score=" + score);
				foodX = (int) (Math.random() * 600);
				foodY = (int) (Math.random() * 600);

				f = new Rectangle(foodX, foodY);
				repaint();
			}

			if (x == 0 || y == 0 || x + size == getWidth()
					|| y + size == getHeight()) {

				scoreTime.stop();
				JOptionPane.showMessageDialog(panel, "Game Over");
				System.exit(0);
			}
			repaint();
		}

	}

	private class Action2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			size-=1;//decrease size every 15 seconds
			if (s.intersects(f)) {
				foodTime.start();
			}
			foodX = (int) (Math.random() * 600);
			foodY = (int) (Math.random() * 600);

			repaint();

		}

	}

	public void resizeSnake() {
		Rectangle[] copy = new Rectangle[point.length + 1];
		for (int i = 0; i < point.length; i++) {

			copy[i] = point[i];
		}
		copy[point.length-1]=new Rectangle(x,y,size,size);
		point = copy;

	}

	public void addRectangle(Rectangle part) {
		point[point.length - 1] = part;
	}

	private class Action3 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			counter++;
			label2.setText("Time: " + counter);
			repaint();
		}

	}

	private class Key implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				deltaX = 1;
				deltaY = 0;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				deltaX = -1;
				deltaY = 0;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				deltaX = 0;
				deltaY = 1;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				deltaX = 0;
				deltaY = -1;
			}

			repaint();

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

	public static void main(String[] args) {

		new Snake();
	}

}
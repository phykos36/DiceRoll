package diceroll;
import java.util.*;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
@SuppressWarnings("serial")
public class DiceRoll extends Frame implements ActionListener{
    private static final int WIDTH  = 400;
    private static final int HEIGHT = 400;
    private int[] results = new int[5];
    private Random random = new Random();
    TextField timesText;
    TextField maxText;
    Label[] resultLabel = {new Label(" "), new Label("履歴が"), new Label("ここに"), new Label("表示"), new Label("されます")};
    Panel toolBoard = new Panel();
    Panel p1 = new Panel();
    Panel p2 = new Panel();
    public DiceRoll(){
    	setTitle("ダイスロール");
		setSize(WIDTH, HEIGHT);
		setLayout(new GridLayout(6, 1));
		toolBoard.setLayout(new GridLayout(1, 5));
		p1.setLayout(new GridLayout(1, 3));
		timesText = new TextField("2");
		timesText.setFont(new Font("M S ゴシック", Font.PLAIN, 30));
		p1.add(timesText);
		Label dlabel = new Label("D",Label.CENTER);
		dlabel.setFont(new Font("M S ゴシック", Font.PLAIN, 30));
		p1.add(dlabel);
		maxText = new TextField("6");
		maxText.setFont(new Font("M S ゴシック", Font.PLAIN, 30));
		p1.add(maxText);
		toolBoard.add(p1);
		p2.setLayout(new GridLayout(1, 2));
		Button roll = new Button("Roll!!");
		roll.setFont(new Font("M S ゴシック", Font.PLAIN, 20));
		p2.add(roll);
		roll.addActionListener(this);
		Button close = new Button("close");
		close.setFont(new Font("M S ゴシック", Font.PLAIN, 20));
		p2.add(close);
		toolBoard.add(p2);
		close.addActionListener(this);
		add(toolBoard);
		for(Label xLabel: resultLabel) {
			add(xLabel);
			xLabel.setFont(new Font("M S ゴシック", Font.PLAIN, 30));
		}
		setVisible(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		String commandName = e.getActionCommand();
		if(commandName.equals("close")) {
			System.exit(0);
		}else if(commandName.equals("Roll!!")) {
			roll(Integer.parseInt(timesText.getText()), Integer.parseInt(maxText.getText()));
			for(int i=0; i<5; i++)
				if(results[i]!=0)
					resultLabel[i].setText(String.valueOf(results[i]));
			loadAndPlayAudio();
		}
	}
	public void roll(int times, int max) {
		int result = 0;
		for(int i=0; i<times; i++)
			result += random.nextInt(max)+1;
		addResult(result);
	}
	public void addResult(int x) {
		for(int i=4; i>0; i--)
			if(results[i-1]!=0)
				results[i] = results[i-1];
		results[0] = x;
	}
	private void loadAndPlayAudio()
    {   try
        {   AudioInputStream sound = AudioSystem.getAudioInputStream(
                getClass().getResource("dicesound.wav"));
            AudioFormat format = sound.getFormat();
            DataLine.Info di = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(di);
            clip.open(sound);
            clip.start();
        }
        catch(UnsupportedAudioFileException uafe)
        {   uafe.printStackTrace();  }
        catch(IOException ioe)
        {   ioe.printStackTrace();  }
        catch(LineUnavailableException lue)
        {   lue.printStackTrace();  }
    }

	public static void main(String[] args) {
		new DiceRoll();
	}
}

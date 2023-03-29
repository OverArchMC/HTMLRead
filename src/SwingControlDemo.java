import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;

public class SwingControlDemo implements ActionListener {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea ta, ta2;
    private JTextField taResult;
    private int WIDTH=800;
    private int HEIGHT=700;
    private JScrollPane scrollPane;
    private JTextArea link, term, result;


    public SwingControlDemo() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.showEventDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(2, 1));


        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        //mb.add(file);
        //mb.add(edit);
        //mb.add(help);

        ta = new JTextArea();
        ta.setBounds(10, 5, (WIDTH-100)/2, HEIGHT-50);
        //mainFrame.add(mb);
        //mainFrame.add(ta);
        mainFrame.setJMenuBar(mb);

        taResult = new JTextField();
        //taResult.setBounds(10, HEIGHT-50, WIDTH-10, HEIGHT);
        //mainFrame.add(taResult);

        ta2 = new JTextArea();
        ta2.setBounds(20 + (WIDTH-100)/2, 5, (WIDTH-100)/2, HEIGHT-50);
        //mainFrame.add(ta2);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);
        scrollPane = new JScrollPane();

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());








        JPanel border = new JPanel(new BorderLayout());
        JButton retrieveButton = new JButton("Retrieve");
        retrieveButton.setActionCommand("Retrieve");
        retrieveButton.addActionListener(new ButtonClickListener());
        border.add(retrieveButton, BorderLayout.SOUTH);
        border.add(new JLabel("Link:", SwingConstants.CENTER), BorderLayout.NORTH);


        JPanel spacer = new JPanel(new GridLayout(2, 1)); // [1]
        JPanel input = new JPanel(new BorderLayout()); // [2]



        link = new JTextArea(); // 2-1
        term = new JTextArea(); // 3-2
        link.setLineWrap(true); // ~
        input.add(term, BorderLayout.CENTER);
        input.add(new JLabel("Keyword:", SwingConstants.CENTER), BorderLayout.NORTH);
        spacer.add(link);
        spacer.add(input);
        spacer.setBounds(0, 0, 800, 100);
        border.add(spacer, BorderLayout.CENTER);



       // mainFrame.add(headerLabel);
        //mainFrame.add(controlPanel);
        //mainFrame.add(scrollPane);
        //mainFrame.add(statusLabel);


        result = new JTextArea();
        result.setLineWrap(true); // ~
        result.setEditable(false);
        scrollPane = new JScrollPane(result);



        mainFrame.add(border);
        mainFrame.add(scrollPane);
        mainFrame.setVisible(true);
    }

    private void showEventDemo() {
        headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("Retrieve");
        JButton submitButton = new JButton("");
        JButton cancelButton = new JButton("");


        okButton.setActionCommand("OK");
        //submitButton.setActionCommand("Submit");
        //cancelButton.setActionCommand("Cancel");

        okButton.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());

        //controlPanel.add(okButton);
        //controlPanel.add(submitButton);
        //controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            ta.cut();
        if (e.getSource() == paste)
            ta.paste();
        if (e.getSource() == copy)
            ta.copy();
        if (e.getSource() == selectAll)
            ta.selectAll();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Retrieve")) {
                //statusLabel.setText("Ok Button clicked.");
                String url = link.getText();
                String contains = term.getText();
                scrollPane.createVerticalScrollBar();
                result.setText(HtmlRead(url, contains));
                //scrollPane.add();
                        //.setText(HtmlRead(url, contains));
                System.out.println(HtmlRead(url, contains));
            }
        }
    }

    public String HtmlRead(String theurl, String contains) {
        String result = "";
        try {
            //System.out.println();
            //System.out.print("hello \n");

            //String contains = "athletics";
            contains = contains.toLowerCase();


            URL url = new URL(theurl);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ( (line = reader.readLine()) != null ) {
                String[] temp = line.split("\'");
                for(int i = 0; i < temp.length; i++){
                    if(temp[i].indexOf("https://") == 0 && temp[i].contains(contains)){
                        result = result + temp[i] + "\n\n";
                    }

                }
                String[] temp2 = line.split("\"");
                for(int i = 0; i < temp2.length; i++){
                    if(temp2[i].indexOf("https://") == 0 && temp2[i].contains(contains)){
                        result += temp2[i] + "\n\n";
                    }

                }
                //System.out.println(line);
            }
            reader.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }

        return result;
    }
}
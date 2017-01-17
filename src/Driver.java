import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.aspose.slides.AsposeLicenseException;

public class Driver extends JFrame {
	File ppt = null;
	JFrame frame = null;

	public Driver(String[] args) throws AsposeLicenseException, FileNotFoundException {
		String path = new File(
				URLDecoder.decode(getClass().getClassLoader().getResource("Aspose.Slides.lic").getPath()))
						.getAbsolutePath();

		// String path = args[0];
		com.aspose.slides.License license = new com.aspose.slides.License();
		license.setLicense(new java.io.FileInputStream(path));
		if (license.isLicensed()) {
			System.out.println("Done initiating");

		} else {
			System.out.println("can not initiate");
		}

		frame = this;
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		setSize(500, 500);
		setLocationRelativeTo(null);
		JButton btnSelectSlide = new JButton("Select Slide");
		btnSelectSlide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);

					fd.setFilenameFilter(new FilenameFilter() {

						@Override
						public boolean accept(File dir, String name) {

							return Pattern.compile("\\.(pptx|pptx)$").matcher(name.toLowerCase()).find();

						}
					});
					// fd.setFile(null);

					fd.setVisible(true);
					// fd.setFile("*.pptx");
					System.out.println();
					ppt = new File(fd.getDirectory() + fd.getFile());

					/// JFileChooser fileChooser = new JFileChooser();
					// fileChooser.setCurrentDirectory(new
					/// File(System.getProperty("user.home")));
					// int result = fileChooser.showOpenDialog(null);
					// if (result == JFileChooser.APPROVE_OPTION) {
					// ppt = fileChooser.getSelectedFile();

					// }
				} catch (Exception ee) {

				}

			}
		});
		panel.add(btnSelectSlide);

		JButton btnNewButton = new JButton("Save as Text");

		panel.add(btnNewButton);

		JLabel lblStatus = new JLabel("Status: ideal");
		panel.add(lblStatus);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		panel.add(btnExit);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				lblStatus.setText("Status: Processing");
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.SAVE);

							fd.setFilenameFilter(new FilenameFilter() {

								@Override
								public boolean accept(File dir, String name) {
									return name.toLowerCase().endsWith(".txt");
								}
							});
							// fd.setFile(null);
							File file = null;
							fd.setVisible(true);
							if (fd.getFile().toLowerCase().endsWith(".txt"))
								file = new File(fd.getDirectory() + fd.getFile());
							else

								file = new File(fd.getDirectory() + fd.getFile() + ".txt");
							URLExtractor ui = new URLExtractor();
							ui.getFileAspose(new File(ppt.getAbsolutePath()), file);
							lblStatus.setText("Status: Ideal");

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lblStatus.setText("Status: Ideal");

					}
				}).start();
				;

			}
		});
		setVisible(true);

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Driver dr = new Driver(args);

		// URLExtractor.getFileAspose(new File("G:\\test.pptx"), new
		// File("E:\\url" + ".txt"));
		// SetFocusableWindowState(true);
		// setFocusable(true);
		// isFocusableWindow();
		// jFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

}

package com.bitcamp.centro.estetico.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.bitcamp.centro.estetico.models.BeautyCenter;
import com.bitcamp.centro.estetico.models.Employee;
import com.bitcamp.centro.estetico.models.Roles;
import com.bitcamp.centro.estetico.models.User;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.settings.ThemeSettings;
import com.github.weisj.darklaf.theme.Theme;
import com.github.weisj.darklaf.theme.event.ThemeChangeEvent;
import com.github.weisj.darklaf.theme.event.ThemeChangeListener;
import com.github.weisj.darklaf.theme.spec.ColorToneRule;

public class MainFrame {
	private static JTabbedPane mainPane;
	private static User user;
	private static JFrame mainFrame;
	private static JButton themeModeBtn;
	private static final URL lightModeURL = MainFrame.class
			.getResource("/com/bitcamp/centro/estetico/resources/light_mode.png");
	private static final URL darkModeURL = MainFrame.class
			.getResource("/com/bitcamp/centro/estetico/resources/dark_mode.png");
	private static Theme theme = Main.theme;

	public MainFrame(User user, BeautyCenter bc) {
		mainPane = new JTabbedPane();
		MainFrame.user = user;
		mainFrame = new JFrame("Centro Estetico Manager");

		mainFrame.setSize(1300, 768);
		mainFrame.setIconImage(new ImageIcon(
				MainFrame.class.getResource("/com/bitcamp/centro/estetico/resources/bc_icon.png")).getImage());
		mainFrame.setMinimumSize(new Dimension(1024, 768));
		mainFrame.setLocationRelativeTo(null);

		themeModeBtn = new JButton();
		ImageIcon themeIcon = new ImageIcon(
				theme.getColorToneRule() == ColorToneRule.DARK ? lightModeURL : darkModeURL);
		themeIcon = new ImageIcon(themeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		themeModeBtn.setIcon(themeIcon);
		themeModeBtn.setContentAreaFilled(false);
		themeModeBtn.setBorderPainted(false);
		themeModeBtn.addActionListener((l) -> {
			ThemeSettings.showSettingsDialog(mainFrame);
		});
		LafManager.addThemeChangeListener(new ThemeChangeListener() {
			@Override
			public void themeChanged(ThemeChangeEvent e) {
				boolean isDark = LafManager.getTheme().getColorToneRule() == ColorToneRule.DARK;
				ImageIcon newThemeIcon = new ImageIcon(isDark ? lightModeURL : darkModeURL);
				newThemeIcon = new ImageIcon(newThemeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
				themeModeBtn.setIcon(newThemeIcon);
				SwingUtilities.updateComponentTreeUI(mainFrame);
			}
			@Override
			public void themeInstalled(ThemeChangeEvent e) {
				boolean isDark = LafManager.getTheme().getColorToneRule() == ColorToneRule.DARK;
				ImageIcon newThemeIcon = new ImageIcon(isDark ? lightModeURL : darkModeURL);
				newThemeIcon = new ImageIcon(newThemeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
				themeModeBtn.setIcon(newThemeIcon);
				SwingUtilities.updateComponentTreeUI(mainFrame);
			}

		});

		mainPane.addChangeListener(e -> {
			JPanel panel = (JPanel) mainPane.getSelectedComponent();
			if (panel instanceof AbstractBasePanel p) {
				p.refresh();
			}
		});
		mainPane.add(new UserAccessPanel());
		mainPane.add(new CustomerPanel(mainFrame));
		if (user instanceof Employee e) {
			if (e.getRole() == Roles.ADMIN) {
				buildAdminFrame();
			}
		}

		mainFrame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.WEST;
		mainFrame.add(themeModeBtn, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		mainFrame.add(mainPane, gbc);

		mainFrame.setVisible(true);
	}

	public static void buildAdminFrame() {
		mainPane.add(new EmployeePanel(mainFrame));
		mainPane.add(new PrizePanel(mainFrame));
		mainPane.add(new ProductPanel(mainFrame));
		mainPane.add(new TreatmentPanel(mainFrame));
		mainPane.add(new TransactionPanel(mainFrame));
		mainPane.add(new SubscriptionPanel(mainFrame));
		mainPane.add(new VATPanel(mainFrame));
		mainPane.add(new ReservationPanel(mainFrame));
		mainPane.add(new StockPanel(mainFrame));
	}

	public static JFrame getMainFrame() {
		return mainFrame;
	}

	public static User getSessionUser() {
		return user;
	}
}

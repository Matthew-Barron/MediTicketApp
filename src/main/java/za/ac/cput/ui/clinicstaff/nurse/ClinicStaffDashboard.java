package za.ac.cput.ui.clinicstaff.nurse;

import za.ac.cput.api.ApiClientProvider;
import za.ac.cput.session.SessionManager;
import za.ac.cput.ui.AppFrame;
import za.ac.cput.ui.clinicstaff.nurse.pages.*;
import za.ac.cput.ui.layout.NavItem;
import za.ac.cput.ui.layout.Sidebar;
import za.ac.cput.ui.layout.TopHeader;
import za.ac.cput.ui.theme.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ClinicStaffDashboard extends JPanel {

    public static final String CARD_DASHBOARD     = "DASHBOARD";
    public static final String CARD_APPOINTMENTS  = "APPOINTMENTS";
    public static final String CARD_TICKETS       = "TICKETS";
    public static final String CARD_PATIENTS      = "PATIENTS";
    public static final String CARD_PAYMENTS      = "PAYMENTS";
    public static final String CARD_NOTIFICATIONS = "NOTIFICATIONS";
    public static final String CARD_PROFILE       = "PROFILE";

    private final AppFrame appFrame;
    private final CardLayout contentLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentLayout);
    private final TopHeader topHeader = new TopHeader();

    public ClinicStaffDashboard(AppFrame appFrame) {
        this.appFrame = appFrame;
        setLayout(new BorderLayout());
        setBackground(AppTheme.BACKGROUND);

        List<NavItem> navItems = List.of(
                new NavItem(CARD_DASHBOARD, "\uD83C\uDFE0", "Dashboard"),
                new NavItem(CARD_APPOINTMENTS, "\uD83D\uDCC5", "Appointments"),
                new NavItem(CARD_TICKETS, "\uD83C\uDFAB", "Tickets"),
                new NavItem(CARD_PATIENTS, "\uD83E\uDE7A", "Patients"),
                new NavItem(CARD_PAYMENTS, "\uD83D\uDCB3", "Payments"),
                new NavItem(CARD_NOTIFICATIONS, "\uD83D\uDD14", "Notifications"),
                new NavItem(CARD_PROFILE, "\uD83D\uDC64", "Profile")
        );

        Sidebar sidebar = new Sidebar(navItems, CARD_DASHBOARD, this::navigateTo, this::onLogout);

        contentPanel.setBackground(AppTheme.BACKGROUND);
        registerPages();

        JPanel rightSide = new JPanel(new BorderLayout());
        rightSide.setBackground(AppTheme.BACKGROUND);
        rightSide.add(topHeader, BorderLayout.NORTH);
        rightSide.add(contentPanel, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(rightSide, BorderLayout.CENTER);

        navigateTo(CARD_DASHBOARD);
    }

    private void registerPages() {
        contentPanel.add(new DashboardPage(this), CARD_DASHBOARD);
        contentPanel.add(new AppointmentsPage(), CARD_APPOINTMENTS);
        contentPanel.add(new TicketsPage(), CARD_TICKETS);
        contentPanel.add(new PatientsPage(), CARD_PATIENTS);
        contentPanel.add(new PaymentsPage(), CARD_PAYMENTS);
        contentPanel.add(new NotificationsPage(), CARD_NOTIFICATIONS);
        contentPanel.add(new ProfilePage(), CARD_PROFILE);
    }

    /** Swaps the visible content card. */
    public void navigateTo(String cardKey) {
        contentLayout.show(contentPanel, cardKey);
    }

    /** Lets DashboardPage push a live unread-notifications count into the header badge. */
    public void setHeaderUnreadCount(int count) {
        topHeader.setUnreadCount(count);
    }

    private void onLogout() {
        ApiClientProvider.getInstance().getBaseApiClient().clearAuthToken();
        SessionManager.getInstance().clear();
        appFrame.showScreen(AppFrame.SCREEN_LOGIN);
    }
}

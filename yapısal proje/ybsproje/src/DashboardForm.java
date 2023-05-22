import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DashboardForm extends JFrame {
    private JPanel dashboradPanel;
    private JLabel lbAdmin;
    private JButton btnRegister;

    public DashboardForm() {
        setTitle("DashboardForm");
        setContentPane(dashboradPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boolean hasRegistredUsers = connectToDatabase();
        if (hasRegistredUsers) {
            // show Login form
            Loginform loginform = new Loginform(this);
            User user = loginform.user;
            if (user != null) {
                lbAdmin.setText("User: " + user.name);
                setLocationRelativeTo(null);
                setVisible(true);
            } else {
                dispose();
            }
        } else {
            // show Registration form
            RegistrationForm registrationForm = new RegistrationForm(this);
            User user = registrationForm.user;
            if (user != null) {
                lbAdmin.setText("User: " + user.name);
                setLocationRelativeTo(null);
                setVisible(true);
            } else {
                dispose();
            }
        }
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm(DashboardForm. this);
                User user = registrationForm.user;
                if (user != null) {
                    JOptionPane.showConfirmDialog(DashboardForm.this,
                            "new user:" + user.name,
                            "Successful Registration",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private boolean connectToDatabase() {
        boolean hasRegistredUsers = false;

        final String MYSQL_SERVER_URL = "jdbc:mysql://localhost";
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate ("CREATE DATABASE IF NOT EXISTS MyStore ");
            statement.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return  hasRegistredUsers;

    }

    public static void main(String[] args) {
        DashboardForm myForm = new DashboardForm();
    }
}

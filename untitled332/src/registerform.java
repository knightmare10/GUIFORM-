import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class registerform extends JFrame {
    private JTextField registrationFormTextField;
    private JTextField tfName;
    private JTextField tfEmail;
    private JTextField tfAddress;
    private JPasswordField tfPassword;
    private JTextField tfConfirmEmail;
    private JPasswordField tfConfirmPassword;
    private JButton tfOK;
    private JPanel mainpanel;
    private JButton tfcancel;

    public registerform(JFrame parent) {
        super("parent");
        setTitle("registerform");
        setSize(300,300);
        setContentPane(mainpanel);
        setVisible(true);

        tfOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registeruser();

            }
        });
        tfcancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
    }
   public void registeruser() {
        String name = tfName.getText();
        String email = tfEmail.getText();
        String address = tfAddress.getText();
        String password = tfPassword.getText();
        String confirmPassword = tfConfirmPassword.getText();
        String confirmEmail = tfConfirmEmail.getText();

        if(name.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please fill all the fields","try again",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!password.equals(confirmPassword))
        {
            JOptionPane.showMessageDialog(this, "Passwords and conform password should be same","try again",JOptionPane.WARNING_MESSAGE);
            return;
        }
        u=adduserToDatabase(name,email,address,password);
        if(u!=null)
        {
            dispose();

        }
        else{
            JOptionPane.showMessageDialog(this, "user failed to register","try again",JOptionPane.WARNING_MESSAGE);
        }

    }
    public user u;
    public user adduserToDatabase(String name,String email,String address,String passord){
        user u =new user();
        String URL= " jdbc:postgresql://localhost:5432/CMR";
            String USERNAME="postgres";
            String PASSWORD="root";
        try{
            Connection con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Statement st=con.createStatement();
            String sql="insert into users (name, email,confirm email,address,password,confirm password)"+"values(?,?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,address);
            ps.setString(4,passord);

            int addedrows=  ps.executeUpdate();
            if(addedrows>0)
            {
                u.name=name;
                u.email=email;
                u.address=address;
                u.password=passord;

            }


        }catch(Exception e){
            e.printStackTrace();

        }


        return u;
    }
    public static void main(String[] args) {
        registerform r=new registerform(null);
        user u=r.u;
        r.setVisible(true);
        r.setSize(300,300);
        r.setContentPane(r.mainpanel);
        r.setDefaultCloseOperation(EXIT_ON_CLOSE);
        if(u!=null)
        {
            System.out.println("registration failed");
        }
        else {
            System.out.println("registration successful");
        }
    }

}



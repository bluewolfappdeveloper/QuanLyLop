using QuanLyLop.DTO;
using QuanLyLopPC;
using QuanLyLopPC.DAO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace QuanLyLopPC.UC
{
    /// <summary>
    /// Interaction logic for AccountUC.xaml
    /// </summary>
    public partial class AccountUC : UserControl
    {
        public AccountUC()
        {
            InitializeComponent();

            NameUser.Text = LoginWindow.name;
            UserName.Text = LoginWindow.username;
        }


        string repass, pass, opass;
        private void btnupdate_Click(object sender, RoutedEventArgs e)
        {
            if (String.IsNullOrEmpty(NameUser.Text) || String.IsNullOrEmpty(UserName.Text) || String.IsNullOrEmpty(repass) || String.IsNullOrEmpty(pass) || String.IsNullOrEmpty(opass))
                MessageBox.Show("Vui lòng điền đủ thông tin tài khoản và mật khẩu", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
            else
            {
                MessageBoxResult result = MessageBox.Show("Bạn có chắc chắn cập nhật thông tin  cá nhân", "Thông Báo", MessageBoxButton.YesNo, MessageBoxImage.Question);

                if (result == MessageBoxResult.Yes)
                {
                    LoginDAO.Instance.UpdateUser(new LoginDTO(LoginWindow.id, NameUser.Text, UserName.Text, pass));


                    

                    PasswordNew.Password = PasswordOld.Password = RePassword.Password = "";
                }
            }
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            NameUser.Text = LoginWindow.name;
            UserName.Text = LoginWindow.username;

        }

        private void PasswordOld_PasswordChanged(object sender, RoutedEventArgs e)
        {
            opass = PasswordOld.Password;
        }


        private void PasswordNew_PasswordChanged(object sender, RoutedEventArgs e)
        {
            pass = PasswordNew.Password;
        }

        private void NameUser_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void RePassword_PasswordChanged(object sender, RoutedEventArgs e)
        {
            repass = RePassword.Password;
        }
    }
}

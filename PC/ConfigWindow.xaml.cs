using QuanLyLopPC.Class;
using QuanLyLopPC.DAO;
using QuanLyLopPC.UC;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace QuanLyLopPC
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class ConfigWindow : Window
    {
        private string passwordmysql, passwordsqlserver;
        string providerName;
        AppSetting app = new AppSetting();
        public ConfigWindow()
        {
            InitializeComponent();

            providerName = app.GetProviderName("CSDL");

            switch (providerName)
            {
                case "MySql.Data.MySqlClient":
                    cbDatabase.SelectedIndex = 2;
                    GetInfoMySQL(app.GetConnectionString("CSDL"));
                    break;

                case "System.Data.SqlClient":
                    cbDatabase.SelectedIndex = 1;
                    GetInfoSQLServer(app.GetConnectionString("CSDL"));
                    break;


                case "System.Data.SQLite":
                    cbDatabase.SelectedIndex = 0;
                    GetInfoSQLite(app.GetConnectionString("CSDL"));
                    break;
            }

        }

        #region Event

        private void cbDatabase_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            groupSQLite.Visibility = Visibility.Collapsed;
            groupSQLServer.Visibility = Visibility.Collapsed;
            groupMySQL.Visibility = Visibility.Collapsed;

            switch (cbDatabase.SelectedIndex)
            {
                case 0:
                    groupSQLite.Visibility = Visibility.Visible;
                    break;

                case 1:
                    groupSQLServer.Visibility = Visibility.Visible;
                    break;

                case 2:
                    groupMySQL.Visibility = Visibility.Visible;
                    break;

            }
        }

        #region testconnection
        private void btnTestMySQL_Click(object sender, RoutedEventArgs e)
        {
            string ConnectionSTRMySQL = String.Format("Server={0}; Database={1}; username={2}; password={3}; charset=utf8;", txtHost.Text, txtdatabase.Text, txtusername.Text, passwordmysql);

            //if (DataProvider.Instance.TestConnection(ConnectionSTRMySQL, "MySql.Data.MySqlClient"))
            //{
            //    MessageBox.Show("Test Success", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            //}
        }


        private void btnTestSQLite_Click(object sender, RoutedEventArgs e)
        {
            if (File.Exists(txtDatasource.Text) == false)
            {
                MessageBox.Show("Connection Failed", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }


            string ConnectionSTRSQLite = String.Format("Data Source= {0}", txtDatasource.Text);

            //if (DataProvider.Instance.TestConnection(ConnectionSTRSQLite, "System.Data.SQLite"))
            //{
            //    MessageBox.Show("Test Success", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            //}
        }

        private void btnTestSQLServer_Click(object sender, RoutedEventArgs e)
        {
            string ConnectionSTRSQLServer;

            if (String.IsNullOrEmpty(txtusername.Text) && String.IsNullOrEmpty(passwordsqlserver))
            {
                ConnectionSTRSQLServer = String.Format("Data Source={0};Initial Catalog={1};Integrated Security=True", txtServer.Text, txtdatabaseSS.Text);
            }
            else
                ConnectionSTRSQLServer = String.Format("Data Source={0};Initial Catalog={1};Integrated Security=True; username={2}; password={3};", txtServer.Text, txtdatabaseSS.Text, txtusername.Text, passwordsqlserver);

            //if (DataProvider.Instance.TestConnection(ConnectionSTRSQLServer, "System.Data.SqlClient"))
            //{
            //    MessageBox.Show("Test Success", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            //}
        }
        #endregion



        private void txtPasswordSS_PasswordChanged(object sender, RoutedEventArgs e)
        {
            passwordsqlserver = txtPassword.Password;
        }

        private void txtPassword_PasswordChanged(object sender, RoutedEventArgs e)
        {
            passwordmysql = txtPassword.Password;
        }
        #endregion


        #region getInfo

        public void GetInfoMySQL(String ConnectionString)
        {
            Regex re = new Regex(@"Server=(?<server>.+); Database=(?<database>.+); username=(?<username>.+); password=(?<password>.*); charset=utf8;");

            foreach (Match item in re.Matches(ConnectionString))
            {
                txtHost.Text = item.Groups["server"].ToString();
                txtdatabase.Text = item.Groups["database"].ToString();
                txtusername.Text = item.Groups["username"].ToString();
                txtPassword.Password = item.Groups["password"].ToString();
            }

        }

        public void GetInfoSQLite(String ConnectionString)
        {
            Regex re = new Regex(@"Data Source=(?<DataSource>.+)");

            foreach (Match item in re.Matches(ConnectionString))
            {

                txtDatasource.Text = item.Groups["DataSource"].ToString();
            }
        }

        public void GetInfoSQLServer(String ConnectionString)
        {
            string regexString = "";

            if (ConnectionString.Contains("username"))
            {
                regexString = @"Data Source=(?<datasource>.+);Initial Catalog=(?<database>.+);Integrated Security=True; username=(?<username>.+); password=(?<password>.+);";
            }
            else regexString = @"Data Source=(?<datasource>.+);Initial Catalog=(?<database>.+);Integrated Security=True";

            Regex re = new Regex(regexString);

            foreach (Match item in re.Matches(ConnectionString))
            {
                txtServer.Text = item.Groups["datasource"].ToString();
                txtdatabaseSS.Text = item.Groups["database"].ToString();
                txtusername.Text = item.Groups["username"].ToString() ;
                txtPasswordSS.Password = item.Groups["password"].ToString();
            }
        }

        #endregion


        private void SaveData_Click(object sender, RoutedEventArgs e)
        {
            string lines = txtAndressStart.Text.Trim() + "\n" + txtAndressEnd.Text.Trim();

            string filepath = AppDomain.CurrentDomain.BaseDirectory + @"excelconfig.txt";
            StreamWriter writer = new StreamWriter(filepath);
            writer.WriteLine(lines);
            writer.Close();
        }

        private void Window_Closed(object sender, EventArgs e)
        {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.Show();
            this.Hide();
        }

    }
}

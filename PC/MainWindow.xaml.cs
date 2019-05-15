using QuanLyLopPC.UC;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
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
    public partial class MainWindow : Window, INotifyPropertyChanged
    {
        public MainWindow()
        {
            InitializeComponent();
            this.DataContext = this;
            name = LoginWindow.name;
            btnslidemenu_Unchecked(btnslidemenu, new RoutedEventArgs());
        }
        private string name;

        public string TextName
        {
            get { return name; }
            set { name = value; OnPropertyChanged("name"); }
        }


        public event PropertyChangedEventHandler PropertyChanged;
        protected virtual void OnPropertyChanged(string newName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(newName));
            }
        }

        private void btnslidemenu_Checked(object sender, RoutedEventArgs e)
        {
            Storyboard sb = this.FindResource("MenuOpen") as Storyboard;
            //Không cần dòng dưới nếu trong XAML đã thể hiện điều này - Target
            //Storyboard.SetTarget(sb, GridMenu);
            // End Find
            //Start
            sb.Begin();
            hello.IsEnabled = true;

            btnslidemenu.IsChecked = true;



        }

        private void btnslidemenu_Unchecked(object sender, RoutedEventArgs e)
        {
            Storyboard sb = this.FindResource("MenuClose") as Storyboard;
            sb.Begin();
            hello.IsEnabled = false;
            btnslidemenu.IsChecked = false;
        }

        private ClassUC lop;
        private StudentUC hs;
        private FeeUC hp;
         private TakeFee fee;
        private StatisticsUC statistics;
        private AccountUC ac;
        private AboutUs ab;



        private void classitem_Selected(object sender, RoutedEventArgs e)
        {
            pic.Visibility = Visibility.Hidden;
            btnslidemenu.IsChecked = false;
            lop = new ClassUC();
            UCLoaded.Children.Clear();
            UCLoaded.Children.Add(lop);
        }

        private void studentitem_Selected(object sender, RoutedEventArgs e)
        {
            pic.Visibility = Visibility.Hidden;
            btnslidemenu.IsChecked = false;
            hs = new StudentUC();
            UCLoaded.Children.Clear();
            UCLoaded.Children.Add(hs);
        }

        private void closeitem_Selected(object sender, RoutedEventArgs e)
        {
            pic.Visibility = Visibility.Hidden;
            Application.Current.Shutdown();
        }

        private void feeitem_Selected(object sender, RoutedEventArgs e)
        {
            pic.Visibility = Visibility.Hidden;
            btnslidemenu.IsChecked = false;
            hp = new FeeUC();
            UCLoaded.Children.Clear();
            UCLoaded.Children.Add(hp);
        }

        private void takefeeitem_Selected(object sender, RoutedEventArgs e)
        {
            pic.Visibility = Visibility.Hidden;
            btnslidemenu.IsChecked = false;
            fee = new TakeFee();
            UCLoaded.Children.Clear();
            UCLoaded.Children.Add(fee);
        }

        private void ChangeInfo_Selected(object sender, RoutedEventArgs e)
        {
            pic.Visibility = Visibility.Hidden;
            btnslidemenu.IsChecked = false;
            ac = new AccountUC();
            UCLoaded.Children.Clear();
            UCLoaded.Children.Add(ac);
        }

        private void AboutUs_Selected(object sender, RoutedEventArgs e)
        {
            pic.Visibility = Visibility.Hidden;
            btnslidemenu.IsChecked = false;
            ab = new AboutUs();
            UCLoaded.Children.Clear();
            UCLoaded.Children.Add(ab);
        }


        private void homeitem_Selected(object sender, RoutedEventArgs e)
        {
            //MessageBox.Show("Hello World");
            if (this.IsLoaded == true)
            {
                pic.Visibility = Visibility.Visible;
                UCLoaded.Children.Clear();
                btnslidemenu.IsChecked = false;
            }
        }


        private void Statistics_Selected(object sender, RoutedEventArgs e)
        {
            pic.Visibility = Visibility.Hidden;
            btnslidemenu.IsChecked = false;
            statistics = new StatisticsUC();
            UCLoaded.Children.Clear();
            UCLoaded.Children.Add(statistics);
        }

        private void GridMenu_MouseLeave(object sender, MouseEventArgs e)
        {
            btnslidemenu_Unchecked(btnslidemenu, new RoutedEventArgs());
        }

        private void GridMenu_LostFocus(object sender, RoutedEventArgs e)
        {
            btnslidemenu_Unchecked(btnslidemenu, new RoutedEventArgs());
        }

        private void Window_Closed(object sender, EventArgs e)
        {
            Environment.Exit(-1);
        }
    }

}

using Microsoft.Win32;
using OfficeOpenXml;
using QuanLyLop.Tool.ExelClass;
using QuanLyLopPC.Class;
using QuanLyLopPC.DAO;
using QuanLyLopPC.DTO;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace QuanLyLopPC.Tool
{
    /// <summary>
    /// Interaction logic for ExelTool.xaml
    /// </summary>
    public partial class ExelTool : Window
    {
        public ExelTool()
        {
            InitializeComponent();

            LoadClass();
        }


        ObservableCollection<ClassDTO> listclass;
        ObservableCollection<StudentDTO> liststudent;

        private void LoadClass()
        {
            listclass = ClassDAO.Instance.GetClass();

            liststudent = new ObservableCollection<StudentDTO>();
            lvstudent.ItemsSource = liststudent;

            cbclass.ItemsSource = listclass;
            cbclass.DisplayMemberPath = "NameClass";
        }


        private void btnImport_Click(object sender, RoutedEventArgs e)
        {
            string filePath = "";
            // tạo SaveFileDialog để lưu file excel
            OpenFileDialog dialog = new OpenFileDialog();

            // chỉ lọc ra các file có định dạng Excel
            //dialog.Filter = "Excel | *.xlsx | Excel 2003 | *.xls";

            // Nếu mở file và chọn nơi lưu file thành công sẽ lưu đường dẫn lại dùng
            if (dialog.ShowDialog() == true)
            {
                filePath = dialog.FileName;
            }

            // nếu đường dẫn null hoặc rỗng thì báo không hợp lệ và return hàm
            if (string.IsNullOrEmpty(filePath))
            {
                MessageBox.Show("Đường dẫn báo cáo không hợp lệ");
                return;
            }

            if (string.IsNullOrEmpty(txtAndress.Text))
            {
                MessageBox.Show("Vui Lòng điền đầy đủ thông tin", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            try
            {
                //liststudent = new ObservableCollection<StudentDTO>();

                // mở file excel
                var package = new ExcelPackage(new FileInfo(filePath));

                // lấy ra sheet đầu tiên để thao tác
                ExcelWorksheet workSheet = package.Workbook.Worksheets[1];

                string cellAddress = Regex.Replace(txtAndress.Text, @"\s", "");

                if (ExcelAddress.IsValidAddress(cellAddress) == false)
                {
                    MessageBox.Show("Vui Lòng kiểm tra lại địa chỉ Excel", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                ExcelAddress addr = new ExcelAddress(cellAddress);
                var start = addr.Start;
                var end = addr.End;

                liststudent.Clear();
                long STT = liststudent.Count;
                // duyệt tuần tự từ dòng thứ 2 đến dòng cuối cùng của file. lưu ý file excel bắt đầu từ số 1 không phải số 0
                for (int i = start.Row + 1; i <= end.Row; i++)
                {
                    int j= addr.Start.Column;
                    string name = "", phone="";
                    STT++; j++;

                    if (workSheet.Cells[i, j].Value != null)
                        name = workSheet.Cells[i, j++].Value.ToString();
                    if (workSheet.Cells[i, j].Value != null)
                        phone = workSheet.Cells[i, j++].Value.ToString();

                    if (String.IsNullOrEmpty(name) ==false)
                    {
                        StudentDTO studentDTO = new StudentDTO(STT, 0, 0, name, phone);

                        liststudent.Add(studentDTO);
                    }
 


                }

                


                //MessageBox.Show("Thành công !!!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);

            }
            catch (Exception ee)
            {
                //MessageBox.Show(ee.Message);
            }


        }


        private void btnExit_Click(object sender, RoutedEventArgs e)
        {
            this.Hide();
        }


        private void btnImportSQL_Click(object sender, RoutedEventArgs e)
        {
            long IDClass;

            if (liststudent.Count == 0)
            {
                if (cbclass.SelectedIndex > -1)
                {
                    MessageBoxResult dialog = MessageBox.Show("Bạn có muốn xóa tất cả học sinh trong lớp " + cbclass.Text, "Thông báo", MessageBoxButton.OKCancel, MessageBoxImage.Information);

                    if (dialog == MessageBoxResult.Cancel) return;
                    if (dialog == MessageBoxResult.OK)
                    {
                        StudentDAO.Instance.DeleteStudent(listclass.ElementAt(cbclass.SelectedIndex).id);
                        MessageBox.Show("Xóa Thành Công ", "Thông báo", MessageBoxButton.OKCancel, MessageBoxImage.Information);
                        return;
                    }
                }

                return;
            };

            if (cbclass.SelectedIndex == -1)
            {
                if (cbclass.Text == "") return;

                MessageBoxResult dialog = MessageBox.Show("Bạn có muôn thêm tên lớp mới?", "Thông báo", MessageBoxButton.OKCancel, MessageBoxImage.Information);

                if (dialog == MessageBoxResult.Cancel) return;

                ClassDAO.Instance.AddClass(new ClassDTO(0, cbclass.Text, ""));

                listclass = ClassDAO.Instance.GetClass();
                cbclass.ItemsSource = listclass;
                cbclass.DisplayMemberPath = "NameClass";

                IDClass = (int)DataProvider.Instance.ExecuteScalar("select max(id) from class");
            }
            else IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;



            if (Addnew.IsChecked == true)
            {
                StudentDAO.Instance.DeleteStudent(listclass.ElementAt(cbclass.SelectedIndex).id);
                TakeFeeDAO.Instance.DelTakeFeeByIDClass(IDClass);
            } 

            for (int i = 0; i < liststudent.Count; i++)
            {
                StudentDAO.Instance.AddStudent(new StudentDTO(0, IDClass, liststudent.ElementAt(i).NameStudent, liststudent.ElementAt(i).Phone));
            }


            MessageBox.Show("Import vào CSDL thành công!");
        }

        private void btnadd_Click(object sender, RoutedEventArgs e)
        {
            if (txtName.Text == "")
                MessageBox.Show("Vui lòng điền tên học sinh", "Thông Báo", MessageBoxButton.OK, MessageBoxImage.Error);
            else
            {
                 liststudent.Add(new StudentDTO(liststudent.Count + 1, 0 , 0, txtName.Text, txtPhone.Text));
                
            }
        }

        private void btndelete_Click(object sender, RoutedEventArgs e)
        {
            if (lvstudent.SelectedIndex >= 0)
            {
                liststudent.RemoveAt(lvstudent.SelectedIndex);

                long STT = 0;
                foreach (StudentDTO student in liststudent)
                {
                    STT++;
                    student.STT = STT;
                }

                lvstudent.ItemsSource = null;
                lvstudent.ItemsSource = liststudent;
            }
        }

        private void btnupdate_Click(object sender, RoutedEventArgs e)
        {
            if (lvstudent.SelectedIndex >= 0 && txtName.Text != "")
            {
                liststudent.ElementAt(lvstudent.SelectedIndex).NameStudent = txtName.Text;
                liststudent.ElementAt(lvstudent.SelectedIndex).Phone = txtPhone.Text;

                lvstudent.ItemsSource = null;
                lvstudent.ItemsSource = liststudent;
            }
        }
    }




}

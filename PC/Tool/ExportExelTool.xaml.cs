using Microsoft.Win32;
using OfficeOpenXml;
using OfficeOpenXml.Style;
using QuanLyLopPC.DAO;
using QuanLyLopPC.DTO;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
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
using System.Windows.Shapes;

namespace QuanLyLopPC.Tool
{
    /// <summary>
    /// Interaction logic for ExportExelTool.xaml
    /// </summary>
    public partial class ExportExelTool : Window
    {
        public ExportExelTool()
        {
            InitializeComponent();

            LoadClass();
        }

        ObservableCollection<ClassDTO> listclass;
        ObservableCollection<StudentDTO> liststudent;

        private void LoadClass()
        {
            listclass = ClassDAO.Instance.GetClass();

            cbclass.ItemsSource = listclass;
            cbclass.DisplayMemberPath = "NameClass";
        }

        private void LoadStudent()
        {
            if (cbclass.SelectedIndex < 0) return;

            long IDClass = listclass.ElementAt(cbclass.SelectedIndex).id;

            liststudent = StudentDAO.Instance.GetStudent(IDClass);

            lvstudent.ItemsSource =  liststudent;
        }

        private void cbclass_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            LoadStudent();
        }

        private void btnExport_Click(object sender, RoutedEventArgs e)
        {
            string filePath = "";
            // tạo SaveFileDialog để lưu file excel
            SaveFileDialog dialog = new SaveFileDialog();

            // chỉ lọc ra các file có định dạng Excel
            dialog.Filter = "Excel | *.xlsx | Excel 2003 | *.xls";

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

            try
            {
                using (ExcelPackage p = new ExcelPackage())
                {
                    // đặt tên người tạo file
                    p.Workbook.Properties.Author = LoginWindow.name;

                    // đặt tiêu đề cho file
                    p.Workbook.Properties.Title = "Danh sách "+cbclass.Text;

                    //Tạo một sheet để làm việc trên đó
                    p.Workbook.Worksheets.Add(cbclass.Text);

                    // lấy sheet vừa add ra để thao tác
                    ExcelWorksheet ws = p.Workbook.Worksheets[1];

                    // đặt tên cho sheet
                    ws.Name = cbclass.Text;
                    // fontsize mặc định cho cả sheet
                    ws.Cells.Style.Font.Size = 11;
                    // font family mặc định cho cả sheet
                    ws.Cells.Style.Font.Name = "Calibri";

                    // Tạo danh sách các column header
                    string[] arrColumnHeader = {
                                                "STT",
                                                "Họ và tên",
                                                "Số điện thoại"
                };

                    // lấy ra số lượng cột cần dùng dựa vào số lượng header
                    var countColHeader = arrColumnHeader.Count();

                    // merge các column lại từ column 1 đến số column header
                    // gán giá trị cho cell vừa merge là Thống kê thông tni User Kteam
                    ws.Cells[1, 1].Value = cbclass.Text;
                    ws.Cells[1, 1, 1, countColHeader].Merge = true;
                    // in đậm
                    ws.Cells[1, 1, 1, countColHeader].Style.Font.Bold = true;
                    // căn giữa
                    ws.Cells[1, 1, 1, countColHeader].Style.HorizontalAlignment = ExcelHorizontalAlignment.Center;

                    int colIndex = 1;
                    int rowIndex = 2;

                    
                    //tạo các header từ column header đã tạo từ bên trên
                    foreach (var item in arrColumnHeader)
                    {
                        var cell = ws.Cells[rowIndex, colIndex];

                        //set màu thành gray
                        var fill = cell.Style.Fill;
                        fill.PatternType = ExcelFillStyle.Solid;
                        fill.BackgroundColor.SetColor(System.Drawing.Color.LightBlue);

                        //căn chỉnh các border
                        var border = cell.Style.Border;
                        border.Bottom.Style =
                            border.Top.Style =
                            border.Left.Style =
                            border.Right.Style = ExcelBorderStyle.Thin;

                        //gán giá trị
                        cell.Value = item;

                        colIndex++;
                    }

                    // lấy ra danh sách UserInfo từ ItemSource của DataGrid

                   
                    // với mỗi item trong danh sách sẽ ghi trên 1 dòng
                    foreach (var item in liststudent)
                    {
                        // bắt đầu ghi từ cột 1. Excel bắt đầu từ 1 không phải từ 0
                        colIndex = 1;

                        // rowIndex tương ứng từng dòng dữ liệu
                        rowIndex++;

                        for (int k= 0; k<arrColumnHeader.Length; k++)
                        {
                            var cell = ws.Cells[rowIndex, k+1];
                            var border = cell.Style.Border;
                            border.Bottom.Style =
                                border.Top.Style =
                                border.Left.Style =
                                border.Right.Style = ExcelBorderStyle.Thin;
                        }
                        

                        //gán giá trị cho từng cell                                             
                        ws.Cells[rowIndex, colIndex++].Value = item.STT;
                        ws.Cells[rowIndex, colIndex++].Value = item.NameStudent;
                        ws.Cells[rowIndex, colIndex++].Value = item.Phone;

                    }
                    ws.Cells[ws.Dimension.Address].AutoFitColumns();

                    //Lưu file lại
                    Byte[] bin = p.GetAsByteArray();
                    File.WriteAllBytes(filePath, bin);
                }
                MessageBox.Show("Xuất excel thành công!");
            }
            catch (Exception EE)
            {
                MessageBox.Show("Có lỗi khi lưu file!");
            }
        }

        private void btnExit_Click(object sender, RoutedEventArgs e)
        {
            this.Hide();
        }
    }
}

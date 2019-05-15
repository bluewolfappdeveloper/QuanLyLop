using QuanLyLop.DTO;
using QuanLyLopPC.Class;
using QuanLyLopPC.DAO;
using QuanLyLopPC.DTO;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DAO
{
    public class StudentDAO
    {
        private static StudentDAO instance;

        public static StudentDAO Instance { get { if (instance == null) { instance = new StudentDAO(); } return instance; } set => instance = value; }

        public StudentDAO()
        {

        }

        public ObservableCollection<StudentDTO> GetStudent(long idlop)
        {
            long STT = 0;
            ObservableCollection<StudentDTO> liststudent = new ObservableCollection<StudentDTO>();

            String query = String.Format("SELECT * FROM student WHERE idclass = {0} order by NameStudent", idlop);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            foreach (DataRow row in data.Rows)
            {
                STT += 1;
                StudentDTO StudentDTO = new StudentDTO(STT, row);
                liststudent.Add(StudentDTO);
            }

            return liststudent;
        }



        public bool AddStudent(StudentDTO studentDTO)
        {

            string query = String.Format("INSERT INTO student (idclass, namestudent, phonestudent) VALUES ('{0}', N'{1}',N'{2}')", studentDTO.idlop, studentDTO.NameStudent, studentDTO.Phone);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;
           
        }

        public bool DeleteStudent(long idstudent)
        {

            string query = String.Format("DELETE FROM Student WHERE Student.id = {0}; Delete from studenttakefee where idstudent = {0}", idstudent);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public bool UpdateClass(StudentDTO studentDTO)
        {

            string query = String.Format("UPDATE student SET idclass = {1}, namestudent = N'{2}', phonestudent = N'{3}' WHERE student.id = {0}", studentDTO.id, studentDTO.idlop, studentDTO.NameStudent, studentDTO.Phone);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public ObservableCollection<StudentDTO> FindStudent(ObservableCollection<StudentDTO> liststudent, String text)
        {
            ObservableCollection<StudentDTO> liststudentfind = new ObservableCollection<StudentDTO>();
            String findtext = TextFormat.Instance.RemoveAccent(text).ToLower();

            for (int i=0; i< liststudent.Count; i++)
            {
                StudentDTO studentDTO = liststudent.ElementAt(i);
                String studentname = TextFormat.Instance.RemoveAccent(studentDTO.NameStudent).ToLower();
                //Toast.makeText(getApplicationContext(), Integer.toString(studentname.indexOf(findstring)), Toast.LENGTH_LONG).show();

                if (studentname.IndexOf(findtext) >= 0) liststudentfind.Add(studentDTO);
            }

            return liststudentfind;
        }

    }
}

using QuanLyLopPC.Class;
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
    public class TakeFeeDAO
    {
        private static TakeFeeDAO instance;

        public static TakeFeeDAO Instance { get { if (instance == null) { instance = new TakeFeeDAO(); } ; return instance; } private set => instance = value; }

        public TakeFeeDAO()
        {

        }

        public ObservableCollection<StudentInfoFeeDTO> GetStudentFeeInfo(long idlop)
        {
            long STT = 0;
            ObservableCollection<StudentInfoFeeDTO> liststudent = new ObservableCollection<StudentInfoFeeDTO>();

            String query = String.Format("SELECT *," +
                "(SELECT COUNT(*) FROM Fee WHERE (SELECT COUNT(*) FROM StudentTakeFee WHERE idstudent = student.id AND idfee=fee.id AND status =1) <> 0) as 'havefee'," +
                "(SELECT COUNT(*) FROM Fee WHERE (SELECT COUNT(*) FROM StudentTakeFee WHERE idstudent = student.id AND idfee=fee.id AND status =1) = 0) as 'nohave' " +
                "FROM student where idclass = {0}", idlop);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            foreach (DataRow row in data.Rows)
            {
                STT += 1;
                StudentInfoFeeDTO StudentInfoFeeDTO = new StudentInfoFeeDTO(STT, row);
                liststudent.Add(StudentInfoFeeDTO);
            }

            return liststudent;
        }

        public ObservableCollection<StudentInfoFeeDTO> FindStudent(ObservableCollection<StudentInfoFeeDTO> liststudent, String text)
        {
            ObservableCollection<StudentInfoFeeDTO> liststudentfind = new ObservableCollection<StudentInfoFeeDTO>();
            String findtext = TextFormat.Instance.RemoveAccent(text).ToLower();

            for (int i = 0; i < liststudent.Count; i++)
            {
                StudentInfoFeeDTO StudentInfoFeeDTO = liststudent.ElementAt(i);
                String studentname = TextFormat.Instance.RemoveAccent(StudentInfoFeeDTO.NameStudent).ToLower();
                //Toast.makeText(getApplicationContext(), Integer.toString(studentname.indexOf(findstring)), Toast.LENGTH_LONG).show();

                if (studentname.IndexOf(findtext) >= 0) liststudentfind.Add(StudentInfoFeeDTO);
            }

            return liststudentfind;
        }

        public ObservableCollection<StudentInfoFeeTakeDTO> GetStudentInfoFeeTake(long idstudent)
        {
            long STT = 0;
            ObservableCollection<StudentInfoFeeTakeDTO> liststudent = new ObservableCollection<StudentInfoFeeTakeDTO>();

            String query = String.Format("SELECT studenttakefee.id, fee.NameFee, fee.PriceFee, studenttakefee.datetime FROM Fee, studenttakefee WHERE fee.id = studenttakefee.idfee and studenttakefee.idstudent = {0}", idstudent);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            foreach (DataRow row in data.Rows)
            {
                STT += 1;
                StudentInfoFeeTakeDTO studentInfoFeeTakeDTO = new StudentInfoFeeTakeDTO(STT, row);
                liststudent.Add(studentInfoFeeTakeDTO);
            }

            return liststudent;
        }

        public bool AddTakeFee(StudentTakeFeeDTO studenttake)
        {

            string query = String.Format("INSERT INTO studenttakefee (idclass, idstudent, idfee, datetime, status) VALUES ({0}, {1}, {2}, '{3}', {4})", studenttake.idclass, studenttake.idstudent, studenttake.idfee, studenttake.datetime.ToString("yyyyMMdd"), 1);

            long data = DataProvider.Instance.ExecuteNonQuery(query);

            return data > 0;

        }

        public bool DelTakeFee(long idtakefee)
        {

            string query = String.Format("DELETE FROM studenttakefee WHERE studenttakefee.id = {0}", idtakefee);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public bool DelTakeFeeByIDClass(long idclass)
        {

            string query = String.Format("DELETE FROM studenttakefee WHERE studenttakefee.idclass = {0}", idclass);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public bool DelTakeFeeByIDStudent(long idstudent)
        {

            string query = String.Format("DELETE FROM studenttakefee WHERE studenttakefee.idstudent = {0}", idstudent);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public bool DelTakeFeeByIDFee(long idfee)
        {

            string query = String.Format("DELETE FROM studenttakefee WHERE studenttakefee.idfee = {0}", idfee);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public bool UpTakeFee(StudentTakeFeeDTO studentTakeFeeDTO)
        {

            string query = String.Format("UPDATE studenttakefee SET idfee = {1}, datetime = '{2}' WHERE studenttakefee.id = {0}", studentTakeFeeDTO.id, studentTakeFeeDTO.idfee, studentTakeFeeDTO.datetime.ToString("yyyyMMdd"));

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public ObservableCollection<ClassInfoFeeCheck> StudentHaveFee(long idclass, long idfee)
        {
            long STT = 0;
            ObservableCollection<ClassInfoFeeCheck> liststudent = new ObservableCollection<ClassInfoFeeCheck>();

            String query = String.Format("SELECT (select student.namestudent from student where student.id = studenttakefee.idstudent) as 'namestudent', (select student.phonestudent from student where student.id = studenttakefee.idstudent) as 'phonestudent', datetime FROM studenttakefee WHERE idclass = {0} and idfee = {1} order by namestudent", idclass, idfee);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            foreach (DataRow row in data.Rows)
            {
                STT += 1;
                ClassInfoFeeCheck classInfoFee = new ClassInfoFeeCheck(STT, row);
                liststudent.Add(classInfoFee);
            }

            return liststudent;
        }

        public ObservableCollection<ClassInfoFeeUnCheck> StudentNotHaveFee(long idclass, long idfee)
        {
            long STT = 0;
            ObservableCollection<ClassInfoFeeUnCheck> liststudent = new ObservableCollection<ClassInfoFeeUnCheck>();

            String query = String.Format("SELECT namestudent, phonestudent FROM student WHERE student.id not in (select f.idstudent from studenttakefee as f where f.idclass = {0} and f.idfee = {1}) and student.idclass ={0}", idclass, idfee);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            foreach (DataRow row in data.Rows)
            {
                STT += 1;
                ClassInfoFeeUnCheck classInfoFee = new ClassInfoFeeUnCheck(STT, row);
                liststudent.Add(classInfoFee);
            }

            return liststudent;
        }

    }
}


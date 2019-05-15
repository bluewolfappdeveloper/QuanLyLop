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
    public class ClassDAO
    {
        private static ClassDAO instance;

        public static ClassDAO Instance { get { if (instance == null) { instance = new ClassDAO(); } return instance; } set => instance = value; }

        public ClassDAO()
        {

        }

        public ObservableCollection<ClassDTO> GetClass()
        {
            long STT = 0;
            ObservableCollection<ClassDTO> listclass = new ObservableCollection<ClassDTO>();

            String query = "SELECT t.id , t.NameClass , t.FeeClass, (select count(*) from student AS g WHERE g.idclass = t.id ) AS 'CountStudent' FROM class AS t ORDER BY length(t.nameclass), t.nameclass";

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            foreach (DataRow row in data.Rows)
            {
                STT += 1;
                ClassDTO classDTO = new ClassDTO(STT, row);
                listclass.Add(classDTO);
            }

            return listclass;
        }

        public bool AddClass(ClassDTO classDTO)
        {

            string query = String.Format("INSERT INTO class (nameclass, feeclass) VALUES (N'{0}', N'{1}');", classDTO.NameClass, classDTO.FeeClass);

            long data = DataProvider.Instance.ExecuteNonQuery(query);

            return data > 0;
           
        }

        public bool DeleteClass(long idclass)
        {

            string query = String.Format("DELETE FROM class WHERE class.id = {0}; Delete from studenttakefee where idclass = {0}", idclass);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public bool UpdateClass(ClassDTO classDTO)
        {

            string query = String.Format("UPDATE class SET nameclass = N'{1}', feeclass = N'{2}' WHERE class.id = {0}", classDTO.id, classDTO.NameClass, classDTO.FeeClass);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public ObservableCollection<ClassDTO> FindClass(ObservableCollection<ClassDTO> listclass, String text)
        {
            ObservableCollection<ClassDTO> listclassfind =  new ObservableCollection<ClassDTO>();

            String findtext = TextFormat.Instance.RemoveAccent(text).ToLower();

            for (int i = 0; i< listclass.Count; i++)
            {
                ClassDTO classDTO = listclass.ElementAt(i);
                String studentname = TextFormat.Instance.RemoveAccent(classDTO.NameClass).ToLower();
                    //Toast.makeText(getApplicationContext(), Integer.toString(studentname.indexOf(findstring)), Toast.LENGTH_LONG).show();

                    if (studentname.IndexOf(findtext) >= 0) listclassfind.Add(classDTO);
              }

            return listclassfind;
        }

    }
}

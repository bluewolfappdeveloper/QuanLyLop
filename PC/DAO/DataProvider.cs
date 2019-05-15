using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Data.SQLite;
using QuanLyLopPC.Class;

namespace QuanLyLopPC.DAO
{
    public class DataProvider
    {
        private static DataProvider instance;
        public static DataProvider Instance { get { if (instance == null) { instance = new DataProvider(); } return instance; } set => instance = value; }
        String connectionSTR;
        AppSetting app = new AppSetting();

        public DataProvider()
        {
            connectionSTR = app.GetConnectionString("CSDL");
        }

        /*public bool TestConnection(string connectionString, string providerName)
        {
            switch (providerName)
            {
                case "MySql.Data.MySqlClient":
                    {
                        using (MySqlConnection connection = new MySqlConnection(connectionString))
                        {
                            try
                            {
                                connection.Open();
                                connection.Close();

                                AppSetting app = new AppSetting();
                                app.SaveConnectiongString("CSDL", connectionString, "MySql.Data.MySqlClient");

                                return true;
                            }
                            catch (Exception e)
                            {
                                MessageBox.Show(e.ToString(), "Thông báo", MessageBoxButton.OK, MessageBoxImage.Error);
                                return false;
                            }


                        }
                    }
                    break;

                case "System.Data.SqlClient":
                    {
                        //connectionSTR = "Server=.//SQLEXPRESS; Database= QuanLyLop;";
                        using (SqlConnection connection = new SqlConnection(connectionString))
                        {
                            try
                            {
                                connection.Open();
                                connection.Close();

                                AppSetting app = new AppSetting();
                                app.SaveConnectiongString("CSDL", connectionString, "System.Data.SqlClient");

                                return true;
                            }
                            catch (Exception e)
                            {
                                MessageBox.Show(e.ToString(), "Thông báo", MessageBoxButton.OK, MessageBoxImage.Error);
                                return false;
                            }

                        }

                    }
                    break;


                case "System.Data.SQLite":
                    {
                        using (SQLiteConnection connection = new SQLiteConnection(connectionString))
                        {
                            try
                            {
                                connection.Open();
                                connection.Close();

                                AppSetting app = new AppSetting();
                                app.SaveConnectiongString("CSDL", connectionString, "System.Data.SQLite");

                                return true;
                            }
                            catch (Exception e)
                            {
                                MessageBox.Show(e.ToString(), "Thông báo", MessageBoxButton.OK, MessageBoxImage.Error);
                                return false;
                            }

                        }

                    }
                    break;


                default:
                    return false;
                    break;
            }
        }*/

        public DataTable ExecuteQuery(string query, object[] parameter = null)
        {
            DataTable data = new DataTable();

            using (SQLiteConnection connection = new SQLiteConnection(connectionSTR))
            {
                connection.Open();

                query = query.Replace("N'", "'");

                SQLiteCommand command = new SQLiteCommand(query, connection);

                if (parameter != null)
                {
                    string[] listPara = query.Split(' ');
                    int i = 0;
                    foreach (string item in listPara)
                    {
                        if (item.Contains('@'))
                        {
                            command.Parameters.AddWithValue(item, parameter[i]);
                            i++;
                        }


                    }
                }


                SQLiteDataAdapter adapter = new SQLiteDataAdapter(command);

                adapter.Fill(data);

                connection.Close();
            }
            return data;
        }

        public int ExecuteNonQuery(string query, object[] parameter = null)
        {
            connectionSTR = app.GetConnectionString("CSDL");
            string providerName = app.GetProviderName("CSDL");

            int data = 0;

            using (SQLiteConnection connection = new SQLiteConnection(connectionSTR))
            {
                connection.Open();

                query = query.Replace("N'", "'");
                SQLiteCommand command = new SQLiteCommand(query, connection);

                if (parameter != null)
                {
                    string[] listPara = query.Split(' ');
                    int i = 0;
                    foreach (string item in listPara)
                    {
                        if (item.Contains('@'))
                        {
                            command.Parameters.AddWithValue(item, parameter[i]);
                            i++;
                        }
                    }
                }

                data = command.ExecuteNonQuery();

                connection.Close();
            }
            return data;
        }

        public object ExecuteScalar(string query, object[] parameter = null)
        {
            object data = 0;

            using (SQLiteConnection connection = new SQLiteConnection(connectionSTR))
            {
                connection.Open();

                SQLiteCommand command = new SQLiteCommand(query, connection);
                query.Replace("N'", "'");

                if (parameter != null)
                {
                    string[] listPara = query.Split(' ');
                    int i = 0;
                    foreach (string item in listPara)
                    {
                        if (item.Contains('@'))
                        {
                            command.Parameters.AddWithValue(item, parameter[i]);
                            i++;
                        }

                    }

                    data = command.ExecuteScalar();

                    connection.Close();
                }

            }
            return data;
        }

    }
}

using MaterialDesignThemes.Wpf;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows.Media;

namespace QuanLyLop
{
    public static class EmptyText
    {
        public static void SetValueColor(ref TextBox text, ref PackIcon icon, bool IsNull)
        {
            if (IsNull)
            {
                text.Foreground = text.BorderBrush = icon.Foreground = Brushes.Red;
            }
            else
            {
                text.Foreground = text.BorderBrush = icon.Foreground = Brushes.Black;
            }
        }

        public static void SetValueColor2(ref PasswordBox text, ref PackIcon icon, bool IsNull)
        {
            if (IsNull)
            {
                text.Foreground = text.BorderBrush = icon.Foreground = Brushes.Red;
            }
            else
            {
                text.Foreground =  text.BorderBrush = icon.Foreground = Brushes.Black;
            }
        } 
    }
}

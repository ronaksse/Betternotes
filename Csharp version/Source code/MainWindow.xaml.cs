using System;
using System.IO;
using System.Runtime.InteropServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using Microsoft.Win32;

namespace _Betternotes
{
    /// <summary>
    ///     Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private static bool _isAudioPlaying;
        private static bool _isVideoPlaying;
        static MediaPlayer _audioPlayer = new MediaPlayer();

        public MainWindow()
        {
            InitializeComponent();
            WindowStyle = WindowStyle.None;
            ResizeMode = ResizeMode.CanResize;
            AllowsTransparency = true;
            ON_LOAD();
        }

        private void ON_LOAD()
        {
            TsTopBar.Opacity = 0.7;
            Back.Opacity = 1;
            Back.Fill = new SolidColorBrush((Color) ColorConverter.ConvertFromString("#FFFAFAFA"));
        }

        #region Moving and resizing

        private void ON_RESIZE(object sender, SizeChangedEventArgs e)
        {
            Back.Width = e.NewSize.Width;
            Back.Height = e.NewSize.Height - 30;
            MuFileList.Height = e.NewSize.Height - 85;
            MuEditor.Width = e.NewSize.Width - 225;
            MuEditor.Height = e.NewSize.Height - 50;
        }

        [DllImport("user32.dll", CharSet = CharSet.Auto)]
        private static extern IntPtr SendMessage(IntPtr hWnd, uint msg, IntPtr wParam, IntPtr lParam);

        [DllImportAttribute("user32.dll")]
        public static extern bool ReleaseCapture();

        private void WINDOW_DRAG(object sender, MouseButtonEventArgs e)
        {
            ReleaseCapture();
            SendMessage(new WindowInteropHelper(this).Handle, 0xA1, (IntPtr) 0x2, (IntPtr) 0);
        }

        private void WINDOW_RESIZE(object sender, MouseButtonEventArgs e)
        {
            var hwndSource = PresentationSource.FromVisual((Visual) sender) as HwndSource;
            SendMessage(hwndSource.Handle, 0x112, (IntPtr) 61448, IntPtr.Zero);
        }

        #endregion

        private void TS_OPEN_ONCLICK(object sender, RoutedEventArgs e)
        {
            var oFd = new OpenFileDialog();
            oFd.Filter =
                "A better note|*.bnote|PNG Image file|*.png|JPEG Image file|*.jpg|JPEG Image file|*.jpeg|Audio file|*.mp3|Video file|*.mp4|All file types|*.*";
            oFd.ShowDialog();

            if (oFd.FileName.EndsWith(".bnote"))
            {
                MuEditor.SelectedIndex = 0;
                TextRange range;
                FileStream fStream;
                if (File.Exists(oFd.FileName))
                {
                    range = new TextRange(RichTextBox1A.Document.ContentStart, RichTextBox1A.Document.ContentEnd);
                    fStream = new FileStream(oFd.FileName, FileMode.OpenOrCreate);
                    range.Load(fStream, DataFormats.XamlPackage);
                    fStream.Close();
                }
            }
            else if (oFd.FileName.EndsWith(".png") || oFd.FileName.EndsWith(".jpg") || oFd.FileName.EndsWith(".jpeg"))
            {
                MuEditor.SelectedIndex = 1;
                if (File.Exists(oFd.FileName))
                {
                    var bitmap = new BitmapImage();
                    bitmap.BeginInit();
                    bitmap.UriSource = new Uri(oFd.FileName);
                    bitmap.EndInit();
                    ImageDisplay1A.Source = bitmap;
                }
            }
            else if (oFd.FileName.EndsWith(".mp3"))
            {
                MuEditor.SelectedIndex = 2;
                if (File.Exists(oFd.FileName))
                {
                    // AudioPlayer1A.Source = new Uri(oFd.FileName);
                    // AudioPlayer2A.Content = "Playing " + oFd.FileName;
                    // AudioPlayer1A.Play();
                    // _isAudioPlaying = true;
                    Uri audioFilePath = new Uri(oFd.FileName);
                    _audioPlayer.Open(audioFilePath);
                    _audioPlayer.Volume = 0.50;
                    _isAudioPlaying = true;
                    AudioPlayer2A.Content = ("File open: " + oFd.FileName);
                }
            }
            else if (oFd.FileName.EndsWith(".mp4"))
            {
                MuEditor.SelectedIndex = 3;
                //Uri videoFilePath = new Uri(oFd.FileName);
                VideoPlayer1A.Source = new Uri(oFd.FileName);
                _isVideoPlaying = true;
                VideoPlayer2A.Content = ("File open: " + oFd.FileName);
            }

            var listBoxItem = new ListBoxItem();
            listBoxItem.Content = oFd.FileName;
            listBoxItem.MouseDoubleClick += OPEN_SELECTED_FILE;
            MuFileList.Items.Insert(0, listBoxItem);
        }

        private void TS_SAVE_ONCLICK(object sender, RoutedEventArgs e)
        {
            try
            {
                var sFd = new SaveFileDialog();
                sFd.Filter = "A better note|*.bnote";
                sFd.ShowDialog();

                TextRange range;
                FileStream fStream;
                range = new TextRange(RichTextBox1A.Document.ContentStart, RichTextBox1A.Document.ContentEnd);
                fStream = new FileStream(sFd.FileName, FileMode.Create);
                range.Save(fStream, DataFormats.XamlPackage);
                fStream.Close();

                var listBoxItem = new ListBoxItem();
                listBoxItem.Content = sFd.FileName;
                listBoxItem.MouseDoubleClick += OPEN_SELECTED_FILE;
                MuFileList.Items.Insert(0, listBoxItem);
            }
            catch (Exception ex)
            {
                //
            }
        }

        #region audio

        private void AUDIO_PLAY(object sender, RoutedEventArgs e)
        {
            _audioPlayer.Position = TimeSpan.Zero;
            _audioPlayer.Play();
            _isAudioPlaying = true;
        }

        private void AUDIO_PAUSE(object sender, RoutedEventArgs e)
        {
            if (_isAudioPlaying)
            {
                _audioPlayer.Pause();
                AudioPlayer2B.Content = "Resume";
                _isAudioPlaying = false;
            }
            else
            {
                _audioPlayer.Play();
                AudioPlayer2B.Content = "Pause";
                _isAudioPlaying = true;
            }
        }

        private void AUDIO_STOP(object sender, RoutedEventArgs e)
        {
            _audioPlayer.Stop();
        }

        private void AUDIO_VOLUME_LOWER(object sender, RoutedEventArgs e)
        {
            _audioPlayer.Volume -= 0.05;
            AudioPlayer3A.Content = (Math.Round(_audioPlayer.Volume*100, 2) + "%");
        }

        private void AUDIO_VOLUME_RAISE(object sender, RoutedEventArgs e)
        {
            _audioPlayer.Volume += 0.05;
            AudioPlayer3A.Content = (Math.Round(_audioPlayer.Volume*100, 2) + "%");
        }

        private void AUDIO_VOLUME_MUTE(object sender, RoutedEventArgs e)
        {
            _audioPlayer.Volume = 0.00;
            AudioPlayer3A.Content = (Math.Round(_audioPlayer.Volume, 2) + "%");
        }
        
        #endregion

        #region video

        private void VIDEO_PLAY(object sender, RoutedEventArgs e)
        {
            VideoPlayer1A.Position = TimeSpan.Zero;
            VideoPlayer1A.Play();
        }

        private void VIDEO_PAUSE(object sender, RoutedEventArgs e)
        {
            if (_isVideoPlaying)
            {
                VideoPlayer1A.Pause();
                VideoPlayer2C.Content = "Resume";
                _isVideoPlaying = false;
            }
            else
            {
                VideoPlayer1A.Play();
                VideoPlayer2C.Content = "Pause";
                _isVideoPlaying = true;
            }
        }

        private void VIDEO_STOP(object sendedr, RoutedEventArgs e)
        {
            VideoPlayer1A.Stop();
        }
        
        private void VIDEO_VOLUME_LOWER(object sender, RoutedEventArgs e)
        {
            VideoPlayer1A.Volume -= 0.05;
            VideoPlayer3A.Content = (Math.Round(VideoPlayer1A.Volume*100, 2) + "%");
        }

        private void VIDEO_VOLUME_RAISE(object sender, RoutedEventArgs e)
        {
            VideoPlayer1A.Volume += 0.05;
            VideoPlayer3A.Content = (Math.Round(VideoPlayer1A.Volume*100, 2) + "%");
        }

        private void VIDEO_VOLUME_MUTE(object sender, RoutedEventArgs e)
        {
            VideoPlayer1A.Volume = 0.00;
            VideoPlayer3A.Content = (Math.Round(VideoPlayer1A.Volume, 2) + "%");
        }

        #endregion

        private void OPEN_SELECTED_FILE(object sender, MouseButtonEventArgs e)
        {
            if (sender is ListBoxItem senderObject)
            {
                if (senderObject.Content.ToString().EndsWith(".bnote"))
                {
                    MuEditor.SelectedIndex = 0;
                    TextRange range;
                    FileStream fStream;
                    range = new TextRange(RichTextBox1A.Document.ContentStart, RichTextBox1A.Document.ContentEnd);
                    fStream = new FileStream(senderObject.Content.ToString(), FileMode.OpenOrCreate);
                    range.Load(fStream, DataFormats.XamlPackage);
                    fStream.Close();
                }
                else if (senderObject.Content.ToString().EndsWith(".png") ||
                         senderObject.Content.ToString().EndsWith(".jpg") ||
                         senderObject.Content.ToString().EndsWith(".jpeg"))
                {
                    MuEditor.SelectedIndex = 1;
                    var bitmap = new BitmapImage();
                    bitmap.BeginInit();
                    bitmap.UriSource = new Uri(senderObject.Content.ToString());
                    bitmap.EndInit();
                    ImageDisplay1A.Source = bitmap;
                }
                else if (senderObject.Content.ToString().EndsWith(".mp3"))
                {
                    MuEditor.SelectedIndex = 2;
                    if (File.Exists(senderObject.Content.ToString()))
                    {
                        // AudioPlayer1A.Source = new Uri(senderObject.Content.ToString());
                        // AudioPlayer2A.Content = "Playing " + senderObject.Content;
                        // AudioPlayer1A.Play();
                        // _isAudioPlaying = true;
                        Uri audioFilePath = new Uri(senderObject.Content.ToString());
                        _audioPlayer.Open(audioFilePath);
                        _audioPlayer.Volume = 0.50;
                        _isAudioPlaying = true;
                        AudioPlayer2A.Content = ("File open: " + senderObject.Content);
                    }
                }
                else if (senderObject.Content.ToString().EndsWith(".mp4"))
                {
                    MuEditor.SelectedIndex = 3;
                    VideoPlayer1A.Source = new Uri(senderObject.Content.ToString());
                    _isVideoPlaying = true;
                    VideoPlayer2A.Content = ("File open: " + senderObject.Content);
                }
            }
        }

        #region other region

        private void MINIMISE_CLICK(object sender, RoutedEventArgs e)
        {
            WindowState = WindowState.Minimized;
        }

        private void MAXIMISE_CLICK(object sender, RoutedEventArgs e)
        {
            if (WindowState == WindowState.Maximized)
            {
                WindowState = WindowState.Normal;
                TsNew.Margin = new Thickness(5, 5, 0, 0);
                TsOpen.Margin = new Thickness(45, 5, 0, 0);
                TsSave.Margin = new Thickness(90, 5, 0, 0);
            }
            else
            {
                WindowState = WindowState.Maximized;
                TsNew.Margin = new Thickness(7, 7, 0, 0);
                TsOpen.Margin = new Thickness(47, 7, 0, 0);
                TsSave.Margin = new Thickness(92, 7, 0, 0);
            }
        }

        private void CLOSE_CLICK(object sender, RoutedEventArgs e)
        {
            Application.Current.Shutdown();
        }

        private void SET_BLACK_TEXT(object sender, MouseEventArgs e)
        {
            /*var senderObject = sender as Button;
            if (senderObject != null)
            {
              
            }
            */
            if (sender is Button senderObject) senderObject.Foreground = Brushes.Black;
        }

        private void SET_WHITE_TEXT(object sender, MouseEventArgs e)
        {
            if (sender is Button senderObject) senderObject.Foreground = Brushes.White;
        }

        #endregion
    }
}
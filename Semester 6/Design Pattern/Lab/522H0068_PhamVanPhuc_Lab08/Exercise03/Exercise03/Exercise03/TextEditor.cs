using System.Windows.Forms;
using System.IO;
using System.Drawing;

namespace Exercise3
{
    public class TextEditor
    {
        private RichTextBox richTextBox;
        private string currentFilePath;
        private string lastSavedContent;

        public TextEditor(RichTextBox rtb)
        {
            richTextBox = rtb;
            lastSavedContent = rtb.Text;
        }

        public string CurrentFilePath { get => currentFilePath; set => currentFilePath = value; }
        public bool HasChanges => richTextBox.Text != lastSavedContent;
        public bool HasSelection => richTextBox.SelectionLength > 0;
        public bool CanPaste => Clipboard.ContainsText();

        public void New()
        {
            richTextBox.Clear();
            CurrentFilePath = string.Empty;
            lastSavedContent = string.Empty;
        }

        public void Open()
        {
            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.Filter = "Text files (*.txt)|*.txt|All files (*.*)|*.*";
                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    richTextBox.Text = File.ReadAllText(openFileDialog.FileName);
                    CurrentFilePath = openFileDialog.FileName;
                    lastSavedContent = richTextBox.Text;
                }
            }
        }

        public void Save()
        {
            if (string.IsNullOrEmpty(CurrentFilePath))
            {
                using (SaveFileDialog saveFileDialog = new SaveFileDialog())
                {
                    saveFileDialog.Filter = "Text files (*.txt)|*.txt|All files (*.*)|*.*";
                    if (saveFileDialog.ShowDialog() == DialogResult.OK)
                    {
                        File.WriteAllText(saveFileDialog.FileName, richTextBox.Text);
                        CurrentFilePath = saveFileDialog.FileName;
                        lastSavedContent = richTextBox.Text;
                    }
                }
            }
            else
            {
                File.WriteAllText(CurrentFilePath, richTextBox.Text);
                lastSavedContent = richTextBox.Text;
            }
        }

        public void Copy() => richTextBox.Copy();
        public void Paste() => richTextBox.Paste();
        public void Delete() => richTextBox.SelectedText = string.Empty;

        public void Bold() => richTextBox.SelectionFont = new Font(
            richTextBox.SelectionFont ?? richTextBox.Font,
            richTextBox.SelectionFont.Style ^ FontStyle.Bold);

        public void Italic() => richTextBox.SelectionFont = new Font(
            richTextBox.SelectionFont ?? richTextBox.Font,
            richTextBox.SelectionFont.Style ^ FontStyle.Italic);

        public void Underline() => richTextBox.SelectionFont = new Font(
            richTextBox.SelectionFont ?? richTextBox.Font,
            richTextBox.SelectionFont.Style ^ FontStyle.Underline);

        public void Color()
        {
            using (ColorDialog colorDialog = new ColorDialog())
            {
                if (colorDialog.ShowDialog() == DialogResult.OK)
                {
                    richTextBox.SelectionColor = colorDialog.Color;
                }
            }
        }
    }
}
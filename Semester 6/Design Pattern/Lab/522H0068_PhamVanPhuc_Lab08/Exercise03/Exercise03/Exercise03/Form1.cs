using System;
using System.Windows.Forms;
using System.Windows.Input;
using Exercise3;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.TextBox;

namespace Exercise3
{
    public partial class Form1 : Form
    {
        private TextEditor editor;
        private ICommand newCommand, openCommand, saveCommand, copyCommand, pasteCommand,
            deleteCommand, boldCommand, italicCommand, underlineCommand, colorCommand;

        public Form1()
        {
            InitializeComponent();
            editor = new TextEditor(richTextBox1);

            // Khởi tạo commands
            newCommand = new NewCommand(editor);
            openCommand = new OpenCommand(editor);
            saveCommand = new SaveCommand(editor);
            copyCommand = new CopyCommand(editor);
            pasteCommand = new PasteCommand(editor);
            deleteCommand = new DeleteCommand(editor);
            boldCommand = new BoldCommand(editor);
            italicCommand = new ItalicCommand(editor);
            underlineCommand = new UnderlineCommand(editor);
            colorCommand = new ColorCommand(editor);

            // Gán sự kiện
            newToolStripMenuItem.Click += (s, e) => newCommand.Execute();
            openToolStripMenuItem.Click += (s, e) => openCommand.Execute();
            saveToolStripMenuItem.Click += (s, e) => saveCommand.Execute();
            copyToolStripMenuItem.Click += (s, e) => copyCommand.Execute();
            pasteToolStripMenuItem.Click += (s, e) => pasteCommand.Execute();
            deleteToolStripMenuItem.Click += (s, e) => deleteCommand.Execute();
            boldToolStripMenuItem.Click += (s, e) => boldCommand.Execute();
            italicToolStripMenuItem.Click += (s, e) => italicCommand.Execute();
            underlineToolStripMenuItem.Click += (s, e) => underlineCommand.Execute();
            colorToolStripMenuItem.Click += (s, e) => colorCommand.Execute();
            openForm2ToolStripMenuItem.Click += (s, e) => new Form1().Show();

            toolStripButtonNew.Click += (s, e) => newCommand.Execute();
            toolStripButtonOpen.Click += (s, e) => openCommand.Execute();
            toolStripButtonSave.Click += (s, e) => saveCommand.Execute();
            toolStripButtonCopy.Click += (s, e) => copyCommand.Execute();
            toolStripButtonPaste.Click += (s, e) => pasteCommand.Execute();
            toolStripButtonBold.Click += (s, e) => boldCommand.Execute();
            toolStripButtonItalic.Click += (s, e) => italicCommand.Execute();
            toolStripButtonUnderline.Click += (s, e) => underlineCommand.Execute();
            toolStripButtonColor.Click += (s, e) => colorCommand.Execute();

            exitToolStripMenuItem.Click += (s, e) => Application.Exit();

            // Theo dõi thay đổi để enable/disable nút
            richTextBox1.TextChanged += UpdateButtonStates;
            richTextBox1.SelectionChanged += UpdateButtonStates;
            UpdateButtonStates(null, null);
        }

        private void UpdateButtonStates(object sender, EventArgs e)
        {
            saveToolStripMenuItem.Enabled = saveCommand.CanExecute();
            toolStripButtonSave.Enabled = saveCommand.CanExecute();

            copyToolStripMenuItem.Enabled = copyCommand.CanExecute();
            toolStripButtonCopy.Enabled = copyCommand.CanExecute();

            pasteToolStripMenuItem.Enabled = pasteCommand.CanExecute();
            toolStripButtonPaste.Enabled = pasteCommand.CanExecute();

            deleteToolStripMenuItem.Enabled = deleteCommand.CanExecute();

            boldToolStripMenuItem.Enabled = boldCommand.CanExecute();
            toolStripButtonBold.Enabled = boldCommand.CanExecute();

            italicToolStripMenuItem.Enabled = italicCommand.CanExecute();
            toolStripButtonItalic.Enabled = italicCommand.CanExecute();

            underlineToolStripMenuItem.Enabled = underlineCommand.CanExecute();
            toolStripButtonUnderline.Enabled = underlineCommand.CanExecute();

            colorToolStripMenuItem.Enabled = colorCommand.CanExecute();
            toolStripButtonColor.Enabled = colorCommand.CanExecute();

            this.Text = $"Simple Notepad - {(string.IsNullOrEmpty(editor.CurrentFilePath) ? "New Document" : System.IO.Path.GetFileName(editor.CurrentFilePath))}";
        }
    }
}
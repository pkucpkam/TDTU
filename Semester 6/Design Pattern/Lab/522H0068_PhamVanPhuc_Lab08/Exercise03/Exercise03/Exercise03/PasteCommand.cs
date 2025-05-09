using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class PasteCommand : ICommand
    {
        private TextEditor editor;

        public PasteCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Paste();

        public bool CanExecute() => editor.CanPaste;
    }
}

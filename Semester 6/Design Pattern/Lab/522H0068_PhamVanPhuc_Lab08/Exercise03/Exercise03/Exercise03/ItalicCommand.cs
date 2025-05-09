using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class ItalicCommand : ICommand
    {
        private TextEditor editor;

        public ItalicCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Italic();

        public bool CanExecute() => editor.HasSelection;
    }
}

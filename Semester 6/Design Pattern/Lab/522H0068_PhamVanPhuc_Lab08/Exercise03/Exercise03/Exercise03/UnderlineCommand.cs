using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class UnderlineCommand : ICommand
    {
        private TextEditor editor;

        public UnderlineCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Underline();

        public bool CanExecute() => editor.HasSelection;
    }
}
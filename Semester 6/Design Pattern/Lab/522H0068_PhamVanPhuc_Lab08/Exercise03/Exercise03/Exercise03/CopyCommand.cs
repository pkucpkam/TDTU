using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class CopyCommand : ICommand
    {
        private TextEditor editor;

        public CopyCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Copy();

        public bool CanExecute() => editor.HasSelection;
    }
}

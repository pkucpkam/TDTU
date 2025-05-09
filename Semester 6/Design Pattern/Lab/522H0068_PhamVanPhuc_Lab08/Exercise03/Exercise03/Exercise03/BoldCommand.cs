using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class BoldCommand : ICommand
    {
        private TextEditor editor;

        public BoldCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Bold();

        public bool CanExecute() => editor.HasSelection;
    }
}

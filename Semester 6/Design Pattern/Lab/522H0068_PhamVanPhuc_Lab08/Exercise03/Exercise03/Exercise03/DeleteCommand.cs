using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class DeleteCommand : ICommand
    {
        private TextEditor editor;

        public DeleteCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Delete();

        public bool CanExecute() => editor.HasSelection;
    }
}

using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class SaveCommand : ICommand
    {
        private TextEditor editor;

        public SaveCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Save();

        public bool CanExecute() => editor.HasChanges;
    }
}
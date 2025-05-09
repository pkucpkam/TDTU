using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class NewCommand : ICommand
    {
        private TextEditor editor;

        public NewCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.New();

        public bool CanExecute() => true;
    }
}

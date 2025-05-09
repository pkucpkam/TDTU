using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class OpenCommand : ICommand
    {
        private TextEditor editor;

        public OpenCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Open();

        public bool CanExecute() => true;
    }
}

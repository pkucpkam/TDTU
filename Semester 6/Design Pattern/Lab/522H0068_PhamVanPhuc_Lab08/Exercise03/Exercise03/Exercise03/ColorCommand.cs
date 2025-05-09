using Exercise3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise3
{
    public class ColorCommand : ICommand
    {
        private TextEditor editor;

        public ColorCommand(TextEditor editor)
        {
            this.editor = editor;
        }

        public void Execute() => editor.Color();

        public bool CanExecute() => editor.HasSelection;
    }
}
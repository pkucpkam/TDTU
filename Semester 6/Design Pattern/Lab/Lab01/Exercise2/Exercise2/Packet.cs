using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise2
{
    public class Packet
    {
        public string Originator { get; }
        public string Destination { get; }
        public string Contents { get; }

        public Packet(string originator, string destination, string contents)
        {
            Originator = originator;
            Destination = destination;
            Contents = contents;
        }

        public bool IsOriginator(string nodeName)
        {
            return Originator == nodeName;
        }

        public bool IsDestination(string nodeName)
        {
            return Destination == nodeName;
        }

        public string GetContents()
        {
            return Contents;
        }

        public override string ToString()
        {
            return $"Packet from {Originator} to {Destination}: {Contents}";
        }
    }
}
using Exercise2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

public abstract class Node
{
    public string Name { get; }
    protected Node NextNode { get; set; }

    protected Node(string name)
    {
        Name = name;
    }

    public void InsertNode(Node node)
    {
        NextNode = node;
    }

    public abstract void Accept(Packet packet);
}
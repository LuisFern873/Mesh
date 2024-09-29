
class Vertex {
    double x, y, z;
    HalfEdge outgoingEdge;
    
    Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void print() {
        System.out.println("(" + this.x + "," + this.y + "," + this.z + ")");
    }
}

class HalfEdge {
    Vertex origin;
    HalfEdge twin;
    HalfEdge next;
    Face face;

    HalfEdge(Vertex origin) {
        this.origin = origin;
    }
}

class Face {
    HalfEdge edge;

    Face(HalfEdge edge) {
        this.edge = edge;
    }
}


public class HalfEdgeMesh {


    public static void traverseFaceEdges(HalfEdge start) {

        HalfEdge current = start;
    
        do {
            current.origin.print(); // print edge origin!
    
            current = current.next;
        } while (current != start);
    }
    
    public static void traverseNeighbors(Vertex vertex) {
    
        HalfEdge start = vertex.outgoingEdge;
        HalfEdge current = start;
    
        do {
            current.twin.origin.print(); // print neighbor vertex!
    
            current = current.twin.next;
    
        } while (current != start);
    
    }

    public static void split(HalfEdge edge) {
        Vertex newVertex = new Vertex(
            (edge.origin.x + edge.twin.origin.x) / 2,
            (edge.origin.y + edge.twin.origin.y) / 2,
            (edge.origin.z + edge.twin.origin.z) / 2
        ); 

        edge.twin.origin = newVertex;

        HalfEdge newEdge = new HalfEdge(newVertex);
        HalfEdge newEdgeTwin = new HalfEdge(edge.origin);

        newEdge.twin = newEdgeTwin;
        // coming soon! :)
        
    }


    public static void main(String[] args) {
        // Create vertices
        Vertex v1 = new Vertex(0, 0, 0);
        Vertex v2 = new Vertex(1, 0, 0);
        Vertex v3 = new Vertex(0, 1, 0);



        // Create half-edges
        HalfEdge e1 = new HalfEdge(v1);
        HalfEdge e2 = new HalfEdge(v2);
        HalfEdge e3 = new HalfEdge(v3);
        
        HalfEdge e1Twin = new HalfEdge(v2);  // Twin edge of e1
        HalfEdge e2Twin = new HalfEdge(v3);  // Twin edge of e2
        HalfEdge e3Twin = new HalfEdge(v1);  // Twin edge of e3

        // Set twins
        e1.twin = e1Twin;
        e2.twin = e2Twin;
        e3.twin = e3Twin;
        e1Twin.twin = e1;
        e2Twin.twin = e2;
        e3Twin.twin = e3; 

        // Set next pointers (assume counter-clockwise order for the face)
        e1.next = e2;
        e2.next = e3;
        e3.next = e1;
        e1Twin.next = e3Twin;
        e2Twin.next = e1Twin;
        e3Twin.next = e2Twin;


        // Set outgoing edges
        v1.outgoingEdge = e1;
        v2.outgoingEdge = e2;
        v3.outgoingEdge = e3;

        // Create a face
        Face face = new Face(e1);

        // Set face reference in each half-edge
        e1.face = face;
        e2.face = face;
        e3.face = face;

        // Output a simple representation of the mesh
        System.out.println("Half-Edge Mesh Representation:");
        System.out.println("Face with edges:");
        System.out.println("Edge from (" + e1.origin.x + "," + e1.origin.y + ") to (" + e1.next.origin.x + "," + e1.next.origin.y + ")");
        System.out.println("Edge from (" + e2.origin.x + "," + e2.origin.y + ") to (" + e2.next.origin.x + "," + e2.next.origin.y + ")");
        System.out.println("Edge from (" + e3.origin.x + "," + e3.origin.y + ") to (" + e3.next.origin.x + "," + e3.next.origin.y + ")");


        System.out.println("Traverse face edges: ");
        traverseFaceEdges(e1);

        System.out.println("Traverse neighbor vertices: ");
        traverseNeighbors(v3);
        
    }
}

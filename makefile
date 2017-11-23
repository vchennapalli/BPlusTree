JFLAGS = -g
JC = javac


.SUFFIXES: .java .class


.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        treesearch.java \
        BPlusTree.java \
        Pair.java \
        Node.java \
        LeafNode.java \
        IndexNode.java \
        Stack.java


default: classes


classes: $(CLASSES:.java=.class)


clean:
	$(RM) *.class

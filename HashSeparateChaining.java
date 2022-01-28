/*THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. Tommy Skodje*/

public class HashSeparateChaining {

	private class Node {
		Entry entry;
		Node next;

		Node(Entry entry) { this.entry = entry; }
	}

	Node[] hashTable;
	int arraySize;
	int tableSize;

	public HashSeparateChaining(){
		hashTable = new Node[10];
		arraySize = 10;
	}

	/** TODO: Write a resizing method for the hash table.*/
	private void resize() {
		
		double loadFactor = (double)this.tableSize / this.arraySize;
		
		
		if (loadFactor >= 0.5) {
			System.out.println("doubling array size!");
		this.arraySize *= 2;
		hashTable = new Node[arraySize];
		} else if (loadFactor <= 0.125) {
			System.out.println("halving array size!");
			this.arraySize /= 2;
			hashTable = new Node[arraySize];
		}
		
	}

	/** Computes the index in the hash table from a given key */
	private int hash(String key) {
		int hashCode = key.hashCode();
		return (hashCode & 0x7fffffff) % arraySize;
	}

	/** Returns the number of entries in the hash table. */
	public int size() { return tableSize; }

	/** Checks whether the hash table is empty */
	public boolean isEmpty() { return tableSize == 0; }

	/** Returns the node containing the given key value if it exists in the table.
	    Otherwise, it returns a null value. */
	private Node findEntry(String key) {
		int index = hash(key);

		Node currentNode = hashTable[index];
		while (currentNode != null && !currentNode.entry.getKey().equals(key))
			currentNode = currentNode.next;

		return currentNode;

	}

	/** Returns the integer value paired with the given key, if the key is in the table.
		Otherwise, it returns null. */
	public Integer get(String key) {
		Node searchResult = findEntry(key);

		if (searchResult != null)
			return searchResult.entry.getValue();
		else
			return null;

	}

	/** If the given key is not in the table, creates a new entry and adds it to the table.
		Otherwise, it updates the value associated with the given key. */
	public void put(String key, Integer value) {
		Node searchResult = findEntry(key);

		if (searchResult != null){
			searchResult.entry.setValue(value);
			return;
		}

		Entry newEntry = new Entry(key, value);
		Node newNode = new Node(newEntry);

		int index = hash(key);
		if (hashTable[index] != null) {
			newNode.next = hashTable[index];
			
			//Increase the table size if a new node is created.
			this.tableSize++;
		}
		hashTable[index] = newNode;
		
		
		
		
		//TEST
		double loadFactor = (double)this.tableSize / this.arraySize;
		System.out.println("Load factor: " + loadFactor);
		System.out.println("Table size: " + (double)this.tableSize);
		System.out.println("array Size: " + this.arraySize);
		if (loadFactor >= 0.50 && !(isEmpty())) { //If the load factor >= 0.5, resize.
			resize();
		}

	}

	/** Removes the entry containing the given key
	   from the table, if the key exists in the table. */
	public void delete(String key) {
		Node searchResult = findEntry(key);
		if (searchResult == null)
			return;

		int index = hash(key);
		if (hashTable[index] == searchResult)
			hashTable[index] = searchResult.next;
		else{
			Node currentNode = hashTable[index];
			while (currentNode.next != searchResult)
				currentNode = currentNode.next;
			currentNode.next = searchResult.next;
			
			//Decrease the table size if a node is deleted.
			this.tableSize--;
		}
		
		//TEST
		double loadFactor = this.tableSize / this.arraySize;
		if (loadFactor <= 0.125 && !(isEmpty())) { //If the load factor is <= 0.125, resize.
			resize();
		}
		
	}

	/** Produces a string representation of the table. */
	@Override
	public String toString(){
		String output = "";
		for (int i = 0; i < arraySize; i++){
			output += "\n (" + i + "): ";
			Node currentNode = hashTable[i];
			if (currentNode == null)
				output += currentNode + "\n";
			else{
				while (currentNode != null){
					output += " -> " + currentNode.entry;
					currentNode = currentNode.next;
				}
				output += "\n";
			}
		}

		return output;

	}
}

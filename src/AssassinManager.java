import java.util.List;

/**
 * @author Danny Beaumont and Edwin Armstrong (efa) and Erica Putsche
 * @last updated 10/08/13
 *
 */
public class AssassinManager
{
    /**
     * Node for Assassin Manager
     */
    public class AssassinNode
    {
       String name,killer;
       AssassinNode next;
	   AssassinNode prev;
		 
       AssassinNode(String name,String killer)
       {
           this.name = name;
           this.killer = killer;//killer for graveyard, staking for killRing
           this.next = null;
		   this.prev = null;
       }

    }
	 
    AssassinNode head, deadHead; ///omg grateful dead reference
    
    /**
     * constructor for AssassinManager
     * @author Danny Beaumont and Edwin Armstrong (efa)
     * @param names
     * @throws IllegalArgumentException
     */
    AssassinManager(List<String> names) throws IllegalArgumentException
    {
        this.head = null;
        this.deadHead = null;
		  		  
		  if(names.size() == 0) throw new IllegalArgumentException();  
		  
        for(int count=0; count < names.size(); count++)
        {
			  add(names.get(count),"");//fill in killer later, it can change
        }	 			 
    }

    /**
     *  Adds a name and their potental killer to a Node
	  * @author efa     
	  * @param name
     * @param stalking
     */
    public void add(String name,String stalking)
    { 
		// case 0 
    	if (this.head == null) {
    		head = new AssassinNode(name, stalking);
    		head.next = head;
    		head.prev = head;
    	} else { // case 1+
    		AssassinNode newNode = new AssassinNode(name, stalking);
    		newNode.next = head;
    		newNode.prev = head.prev;
    		
    		head.prev.next = newNode;
    		head.prev = newNode;
    	}
    }
	 
//    /**
//     *  Adds node (person) to grave
//	  * @author efa     
//	  * @param name
//     * @param killer
//     */ 
//	 public void addGrave(String name,String killer)
//    { 
//		 // case 0 
//		if (this.deadHead == null) {
//			deadHead.next = deadHead;
//			deadHead.prev = deadHead;
//		} else { // case 1+ 
//			AssassinNode newDead = new AssassinNode(name, killer);
//			newDead.next = deadHead;
//			newDead.prev = deadHead.prev;
//			
//			deadHead.prev.next = newDead;
//			deadHead.prev = newDead;
//		} 
//    }

    /**
     * prints the kill ring iteratively
     * @author efa     */
    public void printKillRing()
    {
	   if (this.head != null) {
		   AssassinNode pos = head;
		   StringBuilder sb = new StringBuilder();
		   do {
			   sb.append(pos.name + " is stalking " + pos.next.name + "\n");
			   pos = pos.next;
		   } while (pos != head);
		   System.out.print(sb.toString());
	   }
    }
	 
//	 /**
//     * sets and renews the kill ring.
//     * sets who each animal is stalking...
//	  * @author efa
//     */
//    public void setKillers()
//    {
//       //your code should go here
//	   
//    }

    /**
     *
     * prints the graveYard recursively in reverse order
     * @author Danny Beaumont
     */
    public void printGraveyard()
    {
		if(deadHead == null)
		{
			System.out.println();
		}else
		{
			printList(deadHead);
		}
    }
	 
    /**
     * helper for recursive print list
     * @author Danny Beaumont
     * ::: This routine is wrong and needs fixing (efa)
	 * ::: It should print LIFO not FIFO
     */
	 private void printList(AssassinNode node)
	 {
		 StringBuilder sb = new StringBuilder();
		   do {
			   node = node.prev;
			   sb.append("    " + node.name + " Was killed by " + node.killer + "\n");
		   } while (node != deadHead);
		   System.out.print(sb.toString()); 
	 }

    /**
     *  Searches if a name is in the kill ring
	  * @author efa
     * @param name
     * @return boolean
     */
    public boolean killRingContains(String name)
    { 
    	boolean killRingContains = false;
    	if (this.head != null) {
    		AssassinNode pos = head;
    		do {
    			if (pos.name.toLowerCase().equals(name.toLowerCase())) {
    				killRingContains = true;
    			}
    			pos = pos.next;
    		} while (pos != head);
    	}
    	return killRingContains;
    }

   /**
    * searches the graveYard for a person's name
    * @author efa
    * @param name
    * @return
    */
   public boolean graveyardContains(String name)
   {		 
		 if(this.deadHead == null)
		   return false;
		 
       AssassinNode cur = this.deadHead; 					 	 
		 do
       {
		    if(cur.name.toLowerCase().compareTo(name.toLowerCase()) == 0)
          {
             return true;
          }			 				
          cur = cur.next;
			 
       }while(cur != deadHead);//current person in list
		 return false;
   }

   /**
    *  Determines if only 1 person is left in the kill ring
    *  @return boolean
    */
   public boolean gameOver()
   {
       return this.head.next == this.head;
   }

   /**
    * Returns the name of the winner
	 * @author efa
    * @return string
    */
   public String winner()
   {
       if(head.next == head)
           return head.name;
       else
           return null;
   }

   /**
    * moves a person from killring to graveYard list
    * @param name
    */
   public void kill(String name)
   {
     if (killRingContains(name)) {
    	 AssassinNode pos = head; 
    	 
    	 // get node to kill
    	 while (!pos.name.toLowerCase().equals(name.toLowerCase())) {
    		 pos = pos.next;
    		 head = head.next; 
    	 }
    	 
    	 pos.killer = pos.prev.name;
    	 
    	 // kill it!! raaaawwwwr!!!
    	 	// unlinks from killring
    	 head.prev.next = head.next;
    	 head.next.prev = head.prev;
    	 
    	 head = head.next;
    	 
    	 addToGraveyard(pos);    	 
     }
   }
   
   private void addToGraveyard(AssassinNode animal) {
	// add dead to graveyard
  	 // case 0 S
  	 if (deadHead == null) {
  		 deadHead = animal;
  		 deadHead.next = deadHead;
  		 deadHead.prev = deadHead;
  	 } else {
  		 animal.next = deadHead;
  		 animal.prev = deadHead.prev;
  		 
  		 deadHead.prev.next = animal;
  		 deadHead.prev = animal;
  	 }
   }
	
	/**
    * Check to see if list is empty.
    */
	public boolean isEmpty()
   {
     return(head==null);
   }
	
}

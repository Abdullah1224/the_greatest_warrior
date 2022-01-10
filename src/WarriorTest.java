import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarriorTest {

		
        @Test
        public void level() {   
        Warrior tom = new Warrior();
        tom.setExperince(100);
        assertEquals(1,tom.getWarriorLevel());
        assertEquals("Pushover",tom.getWarriorRank());
        tom.setExperince(1000);
        assertEquals(10,tom.getWarriorLevel());
        assertEquals("Novice",tom.getWarriorRank());
        tom.setExperince(10000);
        assertEquals(100,tom.getWarriorLevel());
        assertEquals("Greatest",tom.getWarriorRank());
        
        }
        @Test
        public void exception() {
        Warrior tom = new Warrior();
        IllegalArgumentException exception = assertThrows(
        	    IllegalArgumentException.class, 
        	    () -> { 
        	    	tom.setWarriorLevel(101);
        	    }
        	  );

        	  assertEquals("Invalid level", exception.getMessage());
        
        	   exception = assertThrows(
              	    IllegalArgumentException.class, 
              	    () -> { 
              	    	tom.setWarriorLevel(0);
              	    }
              	  );

              	  assertEquals("Invalid level", exception.getMessage());
              	  
              	  exception = assertThrows(
                 	    IllegalArgumentException.class, 
                 	    () -> { 
                 	    	tom.setExperince(99);
                 	    }
                 	  );

                 	  assertEquals("Experience must be greater than or equal 100 and less than or equal 10000", exception.getMessage());
        
                 	 exception = assertThrows(
                      	    IllegalArgumentException.class, 
                      	    () -> { 
                      	    	tom.setExperince(10001);
                      	    }
                      	  );

                      	  assertEquals("Experience must be greater than or equal 100 and less than or equal 10000", exception.getMessage());
             }
        
        
        
        
        @Test
        public void experience() {
        Warrior tom = new Warrior();
        tom.setExperince(100);
        assertEquals(100, tom.getExperince());
        assertEquals(200, tom.increaseExperience(100));
        tom.setExperince(10000);
        assertEquals(10000,tom.increaseExperience(5000));
        
        }
        
        @Test
        public void battle() {
         Warrior tom1 = new Warrior();
         Warrior tom2 = new Warrior();
         tom1.setWarriorLevel(1);
         tom2.setWarriorLevel(1);
         assertEquals( Warrior.battle(tom1, tom2)[0],"A good fight");
         tom1.setWarriorLevel(1);
         tom2.setWarriorLevel(3);
         assertEquals( Warrior.battle(tom1, tom2)[0],"An intense fight");
         tom1.setWarriorLevel(5);
         tom2.setWarriorLevel(4);
         assertEquals( Warrior.battle(tom1, tom2)[0],"A good fight");
         tom1.setWarriorLevel(3);
         tom2.setWarriorLevel(9);
         assertEquals( Warrior.battle(tom1, tom2)[0],"An intense fight");
         tom1.setWarriorLevel(8);
         tom2.setWarriorLevel(13);
         tom1.getWarriorRank();
         tom2.getWarriorRank();
         assertEquals( Warrior.battle(tom1, tom2)[0],"You've been defeated");
         tom1.setWarriorLevel(6);
         tom2.setWarriorLevel(2);
         tom1.getWarriorRank();
         tom2.getWarriorRank();
         assertEquals( Warrior.battle(tom1, tom2)[0],"Easy fight");
         
         
        }
        @Test
        public void train() {
        Warrior tom = new Warrior();
        tom.setWarriorLevel(6);
        assertEquals("Not strong enough", tom.train("Not strong enough", 100, 7));
        assertEquals("Earn more experience points", tom.train("Earn more experience points", 100, 3));
        }
        




	

}

package team005;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.Upgrade;

public class RobotPlayer {
    public static void run(RobotController rc) {    	
    	while (true) {
            try {
            	
            	//Game Clock
            	int currentRound = Clock.getRoundNum();
            	
            	
            	//Team and robot information
            	Team myTeam = rc.getTeam();
            	RobotType myType = rc.getType();
            	
            	//Map information
            	MapLocation currentLocation = rc.getLocation(); 
            	MapLocation enemyHQLocation = rc.senseEnemyHQLocation();
            	MapLocation allyHQLocation = rc.senseHQLocation();
            	Direction directionToEnemyBase = currentLocation.directionTo(enemyHQLocation);
            	Direction directionToAllyBase = currentLocation.directionTo(allyHQLocation);
            	
            	//Current Surroundings

            	
            	
            	/////////////////////////
            	//Instructions for the HQ
            	/////////////////////////
                if (myType == RobotType.HQ) {
                    if (rc.isActive()) {
                        if (currentRound % 8 == 0)
                        	rc.researchUpgrade(Upgrade.NUKE);
                        else 
                        	if(rc.canMove(Direction.values()[currentRound % 8]))
                        		rc.spawn(Direction.values()[currentRound % 8]);
                        	else{
                        		rc.researchUpgrade(Upgrade.NUKE);
                        		rc.yield();
                        	}
                    }
                }

                
            	///////////////////////////////
            	//Instructions for the Soldier
            	///////////////////////////////
                else if (rc.getType() == RobotType.SOLDIER) {
                	if (rc.isActive()) {
                		MapLocation nextLocation = currentLocation.add(directionToAllyBase.opposite());
                    	if(rc.senseMine(nextLocation) == Team.NEUTRAL
                    			|| rc.senseMine(nextLocation) == myTeam.opponent())
                    		rc.defuseMine(nextLocation);
                    	else if (rc.canMove(directionToAllyBase.opposite())
                    			&& currentRound % 8 == 0)
                    		rc.move(directionToAllyBase.opposite());
                    	else
                    		if(rc.senseMine(currentLocation) == null)
                    			rc.layMine();
                    	rc.yield();
                	}
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
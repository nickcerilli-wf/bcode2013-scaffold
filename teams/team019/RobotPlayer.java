package team019;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.Upgrade;

public class RobotPlayer {
    public static void run(RobotController rc) {
        while (true) {
            try {
            	
                if (rc.getType() == RobotType.HQ) {
                	int currentRound = Clock.getRoundNum();
                    if (rc.isActive()) {
                        Direction dir = Direction.values()[(int)(Math.random()*8)];
                        
                        
                        
                        
                        if (rc.canMove(dir) && currentRound % 5 == 0 && currentRound < 500)
                            rc.spawn(dir);
                        
                        else if (!rc.hasUpgrade(Upgrade.DEFUSION))
                        	rc.researchUpgrade(Upgrade.DEFUSION);
                        
                        else if (!rc.hasUpgrade(Upgrade.PICKAXE))
                        	rc.researchUpgrade(Upgrade.PICKAXE);
                        
                        
                        else if(currentRound > 100)
                        	rc.researchUpgrade(Upgrade.NUKE);
                    }
                }
                else if (rc.getType() == RobotType.ARTILLERY) {
                    if (rc.isActive()) {
                        //rc.attackSquare(rc.getLocation());
                    }
                }

                else if (rc.getType() == RobotType.SOLDIER) {
                    if (rc.isActive()) {
                    	int currentRound = Clock.getRoundNum();
                    	Team myTeam = rc.getTeam();
                    	MapLocation currentLocation = rc.getLocation(); 
                    	MapLocation enemyHQLocation = rc.senseEnemyHQLocation();
                    	Direction directionToEnemyBase = currentLocation.directionTo(enemyHQLocation);
                    	
                    	MapLocation nextLocation = currentLocation.add(directionToEnemyBase);
                    	
                    	
                    	if(rc.senseHQLocation().equals(nextLocation)){
                    		Direction nextDirection = Direction.values()[(int)(Math.random()*8)];
							if(rc.canMove(nextDirection)) {
								rc.move(nextDirection);
							}
                    	}
                    	
                    	else if(rc.senseMine(nextLocation) == Team.NEUTRAL
                    			|| rc.senseMine(nextLocation) == myTeam.opponent() )
                    		rc.defuseMine(nextLocation);

                    		
                    	
                    	else if (Math.random()<0.5 
							&& rc.senseMine(currentLocation) == null) {
								rc.layMine();
						} 
                    	
                    	
                    	else { 
                    		
                    		Robot nearRobots[] =  rc.senseNearbyGameObjects(Robot.class, 2, myTeam.opponent());
                    		
                    		if(nearRobots.length >= 1){
                    			return; //don't move, let them come to me.
                    			
                    		}
                    		
                    		else if(rc.canMove(directionToEnemyBase) &&
									(currentRound < 600 || Math.random() < 0.1)) {
								rc.move(directionToEnemyBase);
							}	
						}
                    }
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
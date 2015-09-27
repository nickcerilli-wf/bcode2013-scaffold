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
            	
                if (rc.getType() == RobotType.HQ) {
                	int currentRound = Clock.getRoundNum();
                    if (rc.isActive()) {
                        Direction dir = Direction.values()[(int)(Math.random()*8)];
                        if (rc.canMove(dir) && currentRound % 10 == 0)
                            rc.spawn(dir);
                        if (!rc.hasUpgrade(Upgrade.DEFUSION))
                        	rc.researchUpgrade(Upgrade.DEFUSION);
                        else if (!rc.hasUpgrade(Upgrade.PICKAXE))
                        	rc.researchUpgrade(Upgrade.PICKAXE);
                        else if(currentRound > 600)
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
                    			|| rc.senseMine(nextLocation) == Team.B)
                    		rc.defuseMine(nextLocation);

                    		
                    	
                    	else if (Math.random()<0.05 
							&& rc.hasUpgrade(Upgrade.PICKAXE)
							&& rc.senseMine(currentLocation) == null) {
								rc.layMine();
						} 
                    	
                    	
                    	else { 
							if(rc.canMove(directionToEnemyBase) && Math.random() < 0.2) {
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
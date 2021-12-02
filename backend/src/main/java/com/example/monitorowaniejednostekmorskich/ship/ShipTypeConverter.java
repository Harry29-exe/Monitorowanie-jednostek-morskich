package com.example.monitorowaniejednostekmorskich.ship;

import java.util.HashMap;
import java.util.Map;

public class ShipTypeConverter {
    private static final Map<Long, String> types = new HashMap<>();

    static {
        types.put(20L, "Wing in ground (WIG), all ships of this type");
        types.put(21L, "Wing in ground (WIG), Hazardous category A");
        types.put(22L, "Wing in ground (WIG), Hazardous category B");
        types.put(23L, "Wing in ground (WIG), Hazardous category C");
        types.put(24L, "Wing in ground (WIG), Hazardous category D");
        types.put(25L, "Wing in ground (WIG), Reserved for future use");
        types.put(26L, "Wing in ground (WIG), Reserved for future use");
        types.put(27L, "Wing in ground (WIG), Reserved for future use");
        types.put(28L, "Wing in ground (WIG), Reserved for future use");
        types.put(29L, "Wing in ground (WIG), Reserved for future use");
        types.put(30L, "Fishing");
        types.put(31L, "Towing");
        types.put(32L, "Towing: length exceeds 200m or breadth exceeds 25m");
        types.put(33L, "Dredging or underwater ops");
        types.put(34L, "Diving ops");
        types.put(35L, "Military ops");
        types.put(36L, "Sailing");
        types.put(37L, "Pleasure Craft");
        types.put(38L, "Reserved");
        types.put(39L, "Reserved");
        types.put(40L, "High speed craft (HSC), all ships of this type");
        types.put(41L, "High speed craft (HSC), Hazardous category A");
        types.put(42L, "High speed craft (HSC), Hazardous category B");
        types.put(43L, "High speed craft (HSC), Hazardous category C");
        types.put(44L, "High speed craft (HSC), Hazardous category D");
        types.put(45L, "High speed craft (HSC), Reserved for future use");
        types.put(46L, "High speed craft (HSC), Reserved for future use");
        types.put(47L, "High speed craft (HSC), Reserved for future use");
        types.put(48L, "High speed craft (HSC), Reserved for future use");
        types.put(49L, "High speed craft (HSC), No additional information");
        types.put(50L, "Pilot Vessel");
        types.put(51L, "Search and Rescue vessel");
        types.put(52L, "Tug");
        types.put(53L, "Port Tender");
        types.put(54L, "Anti-pollution equipment");
        types.put(55L, "Law Enforcement");
        types.put(56L, "Spare - Local Vessel");
        types.put(57L, "Spare - Local Vessel");
        types.put(58L, "Medical Transport");
        types.put(59L, "Noncombatant ship according to RR Resolution No. 18");
        types.put(60L, "Passenger, all ships of this type");
        types.put(61L, "Passenger, Hazardous category A");
        types.put(62L, "Passenger, Hazardous category B");
        types.put(63L, "Passenger, Hazardous category C");
        types.put(64L, "Passenger, Hazardous category D");
        types.put(65L, "Passenger, Reserved for future use");
        types.put(66L, "Passenger, Reserved for future use");
        types.put(67L, "Passenger, Reserved for future use");
        types.put(68L, "Passenger, Reserved for future use");
        types.put(69L, "Passenger, No additional information");
        types.put(70L, "Cargo, all ships of this type");
        types.put(71L, "Cargo, Hazardous category A");
        types.put(72L, "Cargo, Hazardous category B");
        types.put(73L, "Cargo, Hazardous category C");
        types.put(74L, "Cargo, Hazardous category D");
        types.put(75L, "Cargo, Reserved for future use");
        types.put(76L, "Cargo, Reserved for future use");
        types.put(77L, "Cargo, Reserved for future use");
        types.put(78L, "Cargo, Reserved for future use");
        types.put(79L, "Cargo, No additional information");
        types.put(80L, "Tanker, all ships of this type");
        types.put(81L, "Tanker, Hazardous category A");
        types.put(82L, "Tanker, Hazardous category B");
        types.put(83L, "Tanker, Hazardous category C");
        types.put(84L, "Tanker, Hazardous category D");
        types.put(85L, "Tanker, Reserved for future use");
        types.put(86L, "Tanker, Reserved for future use");
        types.put(87L, "Tanker, Reserved for future use");
        types.put(88L, "Tanker, Reserved for future use");
        types.put(89L, "Tanker, No additional information");
        types.put(90L, "Other Type, all ships of this type");
        types.put(91L, "Other Type, Hazardous category A");
        types.put(92L, "Other Type, Hazardous category B");
        types.put(93L, "Other Type, Hazardous category C");
        types.put(94L, "Other Type, Hazardous category D");
        types.put(95L, "Other Type, Reserved for future use");
        types.put(96L, "Other Type, Reserved for future use");
        types.put(97L, "Other Type, Reserved for future use");
        types.put(98L, "Other Type, Reserved for future use");
        types.put(99L, "Other Type, no additional information");
    }

    public static String convertShipType(Long shipType) {
        if (shipType <= 0 || shipType > 99) {
            return "Not available (default)";
        } else if (shipType <= 19) {
            return "Reserved for future use";
        } else {
            return types.get(shipType);
        }
    }
}

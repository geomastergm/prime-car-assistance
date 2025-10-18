class Vehicle {
  final String id;
  final String userId;
  final String manufacturer;
  final String model;
  final String? year;
  final String? color;
  final String? plateNumber;
  final String? vin;

  Vehicle({
    required this.id,
    required this.userId,
    required this.manufacturer,
    required this.model,
    this.year,
    this.color,
    this.plateNumber,
    this.vin,
  });

  factory Vehicle.fromMap(Map<dynamic, dynamic> map, String id) {
    return Vehicle(
      id: id,
      userId: map['userId'] ?? '',
      manufacturer: map['manufacturer'] ?? '',
      model: map['model'] ?? '',
      year: map['year'],
      color: map['color'],
      plateNumber: map['plateNumber'],
      vin: map['vin'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'userId': userId,
      'manufacturer': manufacturer,
      'model': model,
      if (year != null) 'year': year,
      if (color != null) 'color': color,
      if (plateNumber != null) 'plateNumber': plateNumber,
      if (vin != null) 'vin': vin,
    };
  }

  String get displayName => '$manufacturer $model${year != null ? ' ($year)' : ''}';
}

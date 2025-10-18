class ServiceRequest {
  final String id;
  final String serviceType;
  final double latitude;
  final double longitude;
  final String clientName;
  final String clientPhone;
  final String clientId;
  final String status; // pending, accepted, in_progress, completed, cancelled
  final int requestedAt;
  final String? assignedProviderId;
  final String? providerName;
  final String? address;
  final String? description;
  final double? estimatedCost;

  ServiceRequest({
    required this.id,
    required this.serviceType,
    required this.latitude,
    required this.longitude,
    required this.clientName,
    required this.clientPhone,
    required this.clientId,
    required this.status,
    required this.requestedAt,
    this.assignedProviderId,
    this.providerName,
    this.address,
    this.description,
    this.estimatedCost,
  });

  factory ServiceRequest.fromMap(Map<dynamic, dynamic> map, String id) {
    return ServiceRequest(
      id: id,
      serviceType: map['serviceType'] ?? '',
      latitude: (map['latitude'] ?? 0.0).toDouble(),
      longitude: (map['longitude'] ?? 0.0).toDouble(),
      clientName: map['clientName'] ?? '',
      clientPhone: map['clientPhone'] ?? '',
      clientId: map['clientId'] ?? '',
      status: map['status'] ?? 'pending',
      requestedAt: map['requestedAt'] ?? DateTime.now().millisecondsSinceEpoch,
      assignedProviderId: map['assignedProviderId'],
      providerName: map['providerName'],
      address: map['address'],
      description: map['description'],
      estimatedCost: map['estimatedCost']?.toDouble(),
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'serviceType': serviceType,
      'latitude': latitude,
      'longitude': longitude,
      'clientName': clientName,
      'clientPhone': clientPhone,
      'clientId': clientId,
      'status': status,
      'requestedAt': requestedAt,
      if (assignedProviderId != null) 'assignedProviderId': assignedProviderId,
      if (providerName != null) 'providerName': providerName,
      if (address != null) 'address': address,
      if (description != null) 'description': description,
      if (estimatedCost != null) 'estimatedCost': estimatedCost,
    };
  }

  String getStatusText() {
    switch (status) {
      case 'pending':
        return 'მოლოდინში';
      case 'accepted':
        return 'მიღებულია';
      case 'in_progress':
        return 'მიმდინარე';
      case 'completed':
        return 'დასრულებული';
      case 'cancelled':
        return 'გაუქმებული';
      default:
        return status;
    }
  }

  String getServiceTypeText() {
    switch (serviceType) {
      case 'mechanic':
        return 'მექანიკოსი';
      case 'fuel':
        return 'საწვავი';
      case 'battery':
        return 'ბატარეა';
      case 'tire':
        return 'საბურავები';
      default:
        return serviceType;
    }
  }
}

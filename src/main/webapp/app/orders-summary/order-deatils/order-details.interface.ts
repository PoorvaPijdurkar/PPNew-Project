export interface OrderAssignmentData {
  orderNumber: string;
  purchaseQuantity: string;
  assignedBy: string;
  bidRate: string;
  createdDate: string;
  purchasePrice: string;
  createdBy: string;
}

export interface OrdertData {
  orderNumber: string;
  ratePrice: number;
  quantityRequired: number;
  estimatedDate: number;
  orderDate: number;
  callRecords: OrderAssignmentData[];
}

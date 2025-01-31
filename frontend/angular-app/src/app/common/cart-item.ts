import { Product } from "./product";

export class CartItem {
  productId: number;
  name: string;
  imageUrl: string;
  unitPrice: number;
  quantity: number;
  unitsInStock: number;

  constructor(product: Product) {
    this.productId = Number(product.id) || 0;  // Convert ID to number safely
    this.name = product.name ?? 'Unknown Product';
    this.imageUrl = product.imageUrl ?? 'assets/images/default-image.jpg';
    this.unitPrice = product.unitPrice ?? 0;
    this.quantity = 1; // Default quantity
    this.unitsInStock = product.unitsInStock ?? 0; // Ensure unitsInStock is always set
  }
}
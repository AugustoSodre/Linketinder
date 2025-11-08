import { BaseHandler } from './BaseHandler';
import type { ChainResult } from './BaseHandler';

export class NumberRangeHandler extends BaseHandler {
  private field: string
  private min: number
  private max: number
  private message?: string

  constructor(field: string, min: number, max: number, message?: string) {
    super();
    this.field = field
    this.min = min
    this.max = max
    this.message = message
  }

  protected async process(context: Record<string, any>): Promise<ChainResult> {
    const raw = context[this.field];
    const num = Number(raw);
    if (Number.isNaN(num) || num < this.min || num > this.max) {
      return { ok: false, message: this.message ?? `${this.field} fora do intervalo (${this.min}-${this.max})` };
    }
    return { ok: true };
  }
}

import { BaseHandler } from './BaseHandler';
import type { ChainResult } from './BaseHandler';

export class FieldRequiredHandler extends BaseHandler {
  private field: string
  private message?: string

  constructor(field: string, message?: string) {
    super();
    this.field = field
    this.message = message
  }

  protected async process(context: Record<string, any>): Promise<ChainResult> {
    const val = context[this.field];
    if (val === null || val === undefined || String(val).trim() === '') {
      return { ok: false, message: this.message ?? `${this.field} é obrigatório` };
    }
    return { ok: true };
  }
}

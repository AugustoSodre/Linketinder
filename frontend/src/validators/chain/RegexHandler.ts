import { BaseHandler } from './BaseHandler';
import type { ChainResult } from './BaseHandler';

export class RegexHandler extends BaseHandler {
  private field: string
  private regex: RegExp
  private message?: string

  constructor(field: string, regex: RegExp, message?: string) {
    super();
    this.field = field
    this.regex = regex
    this.message = message
  }

  protected async process(context: Record<string, any>): Promise<ChainResult> {
    const val = String(context[this.field] ?? '');
    if (!this.regex.test(val)) return { ok: false, message: this.message ?? `${this.field} inválido` };
    return { ok: true };
  }
}

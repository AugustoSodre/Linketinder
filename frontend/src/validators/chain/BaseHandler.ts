export type ChainResult = { ok: true } | { ok: false; message: string };

export abstract class BaseHandler {
  private nextHandler: BaseHandler | null = null;

  setNext(handler: BaseHandler): BaseHandler {
    this.nextHandler = handler;
    return handler;
  }

  async handle(context: Record<string, any>): Promise<ChainResult> {
    const res = await this.process(context);
    if (!res.ok) return res;
    if (this.nextHandler) return this.nextHandler.handle(context);
    return { ok: true };
  }

  protected abstract process(context: Record<string, any>): Promise<ChainResult>;
}
